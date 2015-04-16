import java.util.{Calendar, Date}

import akka.actor.{Props, Actor, ActorSystem}
import org.jsoup.Jsoup

import scala.collection.mutable

/**
 * Created by liwei on 15-3-17.
 */
object test extends App {
    import collection.JavaConversions.mapAsJavaMap
//    val testString = "参加张钹老师从教57周年暨八十华诞研讨会"
//    println(Jsoup.connect("http://weibo.cn/pennyliang").userAgent("Mozilla")
//      .cookies(CookieKeeper.cookie1).get().text().contains(testString))
//    println(Jsoup.connect("http://weibo.cn/pennyliang").userAgent("Mozilla")
//      .cookies(CookieKeeper.cookie2).get().text().contains(testString))
//    println(Jsoup.connect("http://weibo.cn/pennyliang").userAgent("Mozilla")
//      .cookies(CookieKeeper.cookie3).get().text().contains(testString))
//    println(Jsoup.connect("http://weibo.cn/pennyliang").userAgent("Mozilla")
//      .cookies(CookieKeeper.cookie4).get().text().contains(testString))
//    println(Jsoup.connect("http://weibo.cn/pennyliang").userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36").
  //      cookies(CookieKeeper.cookie5).get().text().contains(testString))
  println(Jsoup.connect("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%E8%A5%BF%E5%AE%89&sort=time&page=1").userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36").
    cookies(CookieKeeper.cookie5).get().text())
  println(Jsoup.connect("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%E8%A5%BF%E5%AE%89&sort=time&page=1").userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko").
    cookies(CookieKeeper.cookie2).get().text())



}
