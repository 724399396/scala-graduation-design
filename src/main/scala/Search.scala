import java.sql.Timestamp
import java.util.concurrent.TimeUnit

import akka.actor.{Actor, Props, ActorSystem}
import akka.routing.RoundRobinPool
import org.jsoup._
import org.jsoup.Connection.Method
import org.jsoup.nodes.{Element, Document}
import org.jsoup.select.Elements
import scala.collection.JavaConversions._
import java.net.URLEncoder

import scala.collection.mutable.ArrayBuffer

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
    val system = ActorSystem("weiboCrawler")
    val crawler = system.actorOf(Props[SearchCrawler].withRouter(RoundRobinPool(8)), name="search_crawler")
    val saver = system.actorOf(Props[SearchSaver], name="search_saver")
    println(saver.getClass)
//    for(keyword <- List("旅游","西安","云南"); rank <- List("hot", "time"))
//      crawler ! (keyword, rank, saver)
  }

  class SearchCrawler extends Actor{
    def receive = {
      case Tuple2(keyword: String,rank: String) =>
        search(keyword, "", rank)
      case x: MicroBlogUser =>

    }
  }

  class SearchSaver extends Actor {
    def receive = {
      case x: MicroBlog =>  DBOperation.saveSearch(x)
    }
  }
}

