import java.sql.Timestamp
import java.util.concurrent.TimeUnit

import org.jsoup._
import org.jsoup.Connection.Method
import org.jsoup.nodes.{Element, Document}
import org.jsoup.select.Elements
import scala.collection.JavaConversions._
import java.net.URLEncoder

import scala.collection.mutable.ArrayBuffer

/**
 * Created by li-wei on 2015/3/24.
 */
object Search {
  def search(keyword: String, user: String, rank: String): Unit = {
    for (i <- 1 to 100)
      for(micro <- HtmlParse.getSearchMicroBlog(keyword,
        HtmlParse.getDoc(String.format("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%s&sort=%s&page=%s",
        URLEncoder.encode(keyword, "utf-8"), rank, i.toString))))
          DBOperation.saveSearch(micro.get)
  }

  def main(args: Array[String]) {
    //search("云南", "", "hot")
    search("旅游", "", "hot")
    search("西安", "", "hot")
    search("兵马俑", "", "hot")
  }
}
