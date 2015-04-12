import java.net.URLEncoder

import HtmlParse._
import akka.actor.{ActorRef, ActorSystem, Props, Actor}
import akka.routing.RoundRobinRouter


/**
 * Created by li-wei on 2015/4/10.
 */
object main extends App {
  start(List("旅游","西安","云南"), List("hot", "time"), "梁斌penny")

  sealed trait CrawlerMessage
  case class Search(keyword: String, rank: String) extends CrawlerMessage
  case class SearchPage(url: String, keyword: String) extends CrawlerMessage
  case class SearchMicro(microBlog: MicroBlog) extends CrawlerMessage
  case class Person(name: String) extends CrawlerMessage
  case class PersonPage(url: String) extends CrawlerMessage
  case class PersonMicro(microBlog: MicroBlog) extends CrawlerMessage
  case class BadMicro() extends CrawlerMessage
  case class End(time: Long) extends CrawlerMessage

  class Crawler extends Actor {
    def receive = {
      case PersonPage(url) =>
        println("fetch: " + url)
        getMicroBlog(getDoc(url)).foreach {
          x => if (x != None) sender ! PersonMicro(x.get) else sender ! BadMicro
        }
      case SearchPage(keyword, url) =>
        println("fetch: " + url)
        getSearchMicroBlog(keyword, getDoc(url)).foreach {
          x => if (x != None) sender ! SearchMicro(x.get) else sender ! BadMicro
        }
    }
  }

  class Master(val nrOfElements: Int, val listener: ActorRef) extends Actor {
    val searchPageNumbers = 100
    val start: Long = System.currentTimeMillis
    var nrOfResults: Int = _
    val workerRouter = context.actorOf(
      Props[Crawler].withRouter(RoundRobinRouter(8)), name = "crawlerRouter")
    def receive = {
      case Search(keyword,rank) =>
        for (i <- 1 to searchPageNumbers)
          workerRouter ! SearchPage(keyword, String.format("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%s&sort=%s&page=%s",
            URLEncoder.encode(keyword, "utf-8"), rank, i.toString))
      case Person(name) =>
        DBOperation.follows(name).foreach {
          x => workerRouter ! PersonPage(x.baseUrl + "?page=1")
        }
        DBOperation.fans(name).foreach {
          x => workerRouter ! PersonPage(x.baseUrl + "?page=1")
        }
      case SearchMicro(micro) =>
        DBOperation.saveSearch(micro)
        nrOfResults += 1
        println("now: %d, total: %d".format(nrOfResults, nrOfElements))
        if (nrOfResults == nrOfElements) {
          listener ! End(System.currentTimeMillis - start)
          context.stop(self)
        }
      case PersonMicro(micro) =>
        DBOperation.save(micro)
        nrOfResults += 1
        println("now: %d, total: %d".format(nrOfResults, nrOfElements))
        if (nrOfResults == nrOfElements) {
          listener ! End(System.currentTimeMillis - start)
          context.stop(self)
        }
      case BadMicro =>
        nrOfResults += 1
        println("now: %d, total: %d".format(nrOfResults, nrOfElements))
        if (nrOfResults == nrOfElements) {
          listener ! End(System.currentTimeMillis - start)
          context.stop(self)
        }
    }
  }

  class Listener extends Actor {
    def receive = {
      case End(time) ⇒
        println("Using time: \t%s"
          .format(time))
        context.system.shutdown()
    }
  }

  def start(keyword: List[String], rank: List[String], name: String) = {
    val system = ActorSystem("SearchSystem")
    val listener = system.actorOf(Props[Listener], name = "listener")
    val nrOfElements = keyword.size * rank.size * 100 * 9 + getPage(DBOperation.fans(name)) * 10
                        + getPage(DBOperation.follows(name)) * 10
    val master = system.actorOf(Props(new Master(nrOfElements, listener)), name="master")
    for(keyword <- keyword; rank <- rank)
      master ! Search(keyword, rank)
    master ! Person(name)
  }

  def getPage(persons: List[MicroBlogUser]): Int = {
    persons.map(x => help(x.maxPage)).sum
  }

  def help(x: Int): Int = {
    if (x < 3) x else 2
  }

}
