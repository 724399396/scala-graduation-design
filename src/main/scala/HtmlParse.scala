import java.sql.Timestamp
import java.util.Random
import java.util.concurrent.TimeUnit

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import scala.collection.JavaConversions.{mapAsJavaMap, asScalaIterator}
import scala.collection.mutable.ArrayBuffer

object HtmlParse {
  val cookies = CookieKeeper.allCookies
  var cookie: Map[String, String] = null
  val random: Random = new Random()
  var error = false;

  def getDoc(url: String): Document = {
    var doc: Document = null
    while (doc == null) {
      try {
        doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36").
          cookies(CookieKeeper.cookie5).get()
      } catch {
        case _: Exception => printf("读取 %s 异常 %n"  ,url)
      } finally {
        TimeUnit.SECONDS.sleep(random.nextInt(10))
      }
    }
    if (!error) {
      if (doc.title().startsWith("帐号异常")) {
        error = true;
        val file = "D:\\work\\mafengwo\\c.mp3";
        Runtime.getRuntime().exec("cmd /c start " + file.replaceAll(" ", "\" \""));
      }
    } else {
      TimeUnit.MINUTES.sleep(10)
    }
    doc
  }

  def getMicroBlog(doc: Document): ArrayBuffer[Option[MicroBlog]] = {
    val blogClasses: Iterator[Element] = doc.select("div.c").iterator()
    var microBlogs: ArrayBuffer[Option[MicroBlog]] = ArrayBuffer()
    blogClasses.filter(_.id().contains("M_")).foreach { x => microBlogs += converse(x,doc)}
    microBlogs
  }
  def getSearchMicroBlog(keyword:String, doc: Document): ArrayBuffer[Option[MicroBlog]] = {
    val blogClasses: Iterator[Element] = doc.select("div.c").iterator()
    var microBlogs: ArrayBuffer[Option[MicroBlog]] = ArrayBuffer()
    blogClasses.filter(_.id().contains("M_")).foreach { x => microBlogs += converse(x,doc,keyword)}
    microBlogs
  }

  private def converse(e: Element, doc: Document, keyword: String = ""): Option[MicroBlog] = {
    val firstDiv: Element = e.child(0)
    val rowContext: String = firstDiv.select("span.ctt").get(0).text.replaceAll("&nbsp;", " ")
    if (!(rowContext.contains("此微博已被作者删除") ||
      rowContext.contains("抱歉，此微博已被删除。") ||
      rowContext.contains("你暂时没有这条微博的查看权限哦"))) {
      val nickName = if (e.select("a.nk").text() != "")
        e.select("a.nk").text() else doc.title.substring(0, doc.title().lastIndexOf("的"))
      val forwards: Elements = firstDiv.select("span.cmt")
      var `type` = ""
      var feedFrom, repostContent: String = null
      if (forwards.size() > 0 && forwards.get(0).childNodes().size() > 2) {
        `type` = "转发"
        feedFrom = forwards.get(0).child(0).text()
        val forwardReasonDiv: Element = e.select("span.cmt:containsOwn(转发理由:)").
          parents().get(0)
        repostContent = ""
        for (node <- forwardReasonDiv.childNodes.iterator())
          if (node.nodeName().equals("#text"))
            repostContent += node.toString.replaceAll("&nbsp;", " ")
      }
      val mediaBox: String = getImageUrl(e)
      val time = getTime(e)
      Some(new MicroBlog(nickName, `type`, rowContext, mediaBox, feedFrom, repostContent, time, keyword))
    } else None
  }

  private def getImageUrl(e: Element): String = {
    val imageUrl: String = e.select("a:containsOwn(原图)").attr("href")
    if (imageUrl.equals("")) null
    else {
      val imageId: String = imageUrl.substring(
        imageUrl.lastIndexOf("=") + 1)
      "http://ww2.sinaimg.cn/large/" + imageId + ".jpg"
    }
  }

  private def getTime(ele: Element): Timestamp = {
    val timeString = ele.select("span.ct").get(0).text().split(" ")(0)
    val time = CrawlerUtil.converseTime(timeString)
    time
  }
}

object UrlGet {
  def getFetchFollows(mainUrl: String): List[MicroBlogUser] = {
    val mainDoc = HtmlParse.getDoc(mainUrl)
    val mainName = mainDoc.title.substring(0, mainDoc.title().lastIndexOf("的"))
    val mainUser = new MicroBlogUser(mainUrl,
      mainName, mainName, getPages(mainDoc))
    DBOperation.saveFol(mainUser)
    mainUser +: getPointUsers(mainUrl, "a:containsOwn(关注)", true, DBOperation.saveFol)
  }

  def getFetchFans(mainUrl: String): List[MicroBlogUser] = {
    val mainDoc = HtmlParse.getDoc(mainUrl)
    val mainName = mainDoc.title.substring(0, mainDoc.title().lastIndexOf("的"))
    val mainUser = new MicroBlogUser(mainUrl,
      mainName, mainName, getPages(mainDoc))
    DBOperation.saveFol(mainUser)
    mainUser +: getPointUsers(mainUrl, "a:containsOwn(粉丝)", true, DBOperation.saveFol)
  }

  def getFollows(mainUrl: String): List[MicroBlogUser] = {
    getPointUsers(mainUrl, "a:containsOwn(关注)", false, DBOperation.saveFol)
  }

  def getFans(mainUrl: String): List[MicroBlogUser] = {
    getPointUsers(mainUrl, "a:containsOwn(粉丝)", false, DBOperation.saveFan)
  }

  def getPointUsers(mainUrl: String, feature: String, pageNeed: Boolean, saver: MicroBlogUser => Int): List[MicroBlogUser] = {
    var users: List[MicroBlogUser] = List()
    val mainDoc = HtmlParse.getDoc(mainUrl)
    val nameEndIndex = mainDoc.title().lastIndexOf("的")
    if (nameEndIndex < 0) return users
    val mainName = mainDoc.title.substring(0, nameEndIndex)
    val baseUrl: String = mainDoc.select("div.tip2").
      select(feature).get(0).absUrl("href")
    val pages = getPages(HtmlParse.getDoc(baseUrl))
    for (i <- 1 to pages) {
      val followDoc = HtmlParse.getDoc(baseUrl + "?page=" + i)
      val followsTables = followDoc.select("table")
      for (followsTable <- followsTables.iterator()) {
        try {
          val follow = followsTable.child(0).child(0).child(1).child(0)
          val url = follow.absUrl("href")
          val user =
            if (!pageNeed) {
              val pages = 0;
              val nickName = follow.text()
              val user = new MicroBlogUser(url, nickName, mainName, pages)
              user
            }
            else {
              val subDoc = HtmlParse.getDoc(url)
              val pages = getPages(subDoc)
              val nickName = subDoc.title.substring(0, subDoc.title().lastIndexOf("的"))
              val user = new MicroBlogUser(url, nickName, mainName, pages)
              user
            }
          //saver(user)
          users = users :+ user
        } catch {
          case _: Throwable => Unit
        }
      }
    }
    users
  }

  def getPages(doc: Document): Int = {
    val elems = doc.select("div.pa")
    if (elems.size() == 0) 1
    else {
      val p = "([\\d]+/)([\\d]+)".r
      var pages = 0
      for (p(num, total) <- p.findFirstIn(elems.get(0).text()))
        pages = total.toInt
      pages
    }
  }
}