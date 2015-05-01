import java.util.{Calendar, Date}

import akka.actor.{Props, Actor, ActorSystem}
import org.jsoup.Jsoup

import scala.collection.mutable

/**
 * Created by liwei on 15-3-17.
 */
object test extends App {
    import collection.JavaConversions.mapAsJavaMap
  println(Jsoup.connect("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%E8%A5%BF%E5%AE%89&sort=time&page=1").userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36").
    cookies(CookieKeeper.cookie5).get().text())
  println(Jsoup.connect("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%E8%A5%BF%E5%AE%89&sort=time&page=1").userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko").
    cookies(CookieKeeper.cookie2).get().text())

  //println(CrawlerUtil.converseTime("48分钟前"))
}
