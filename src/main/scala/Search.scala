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
      for (micro <- HtmlParse.getSearchMicroBlog(keyword,
        HtmlParse.getDoc(String.format("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%s&sort=%s&page=%s",
          URLEncoder.encode(keyword, "utf-8"), rank, i.toString))))
        try {
          DBOperation.saveSearch(micro.get)
        } catch {
          case _: Exception =>
        }
  }

  def main(args: Array[String]) {
    Console.println("开始")
    for(keyword <- List("旅游","西安","云南"); rank <- List("hot", "time"))
        search(keyword, "", rank)
  }
}
