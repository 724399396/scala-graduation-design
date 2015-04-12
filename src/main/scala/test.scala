import java.util.{Calendar, Date}

import akka.actor.{Props, Actor, ActorSystem}
import org.jsoup.Jsoup

import scala.collection.mutable

/**
 * Created by liwei on 15-3-17.
 */
object test extends App {
    import collection.JavaConversions.mapAsJavaMap
    val testString = "参加张钹老师从教57周年暨八十华诞研讨会"
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

//  import java.io.{File, FileWriter}
//
//  lazy val out = new FileWriter(new File("relation.txt"))
//  var i = 0
//  val map = new mutable.HashMap[TwoPerson, Int]()
//  for (fan <- DBOperation.fans("梁斌penny")) {
//    val tmp = new TwoPerson(fan.getNickName, fan.getOfWho)
//    map(tmp) = DBOperation.weighted(tmp)
////    out.write(fan.getNickName + " " + fan.getOfWho + " "
////      + DBOperation.weighted(new TwoPerson(fan.getNickName, fan.getOfWho)) + "\n")
//    i += 1
//  }
//  i = 0
//  for (fol <- DBOperation.follows("梁斌penny")) {
//    val tmp = new TwoPerson(fol.getNickName, fol.getOfWho)
//    map(tmp) = DBOperation.weighted(tmp)
////    out.write(fol.getNickName + " " + fol.getOfWho + " "
////      + DBOperation.weighted(new TwoPerson(fol.getNickName, fol.getOfWho)) + "\n")
//    i += 1
//  }
//  map.filter(numberOrder).foreach {
//    x => out.write(x._1.getNickName + " " + x._1.getFeedName+ " "
//          + x._2 + "\n")
//  }
//  def numberOrder(x:(TwoPerson, Int)):Boolean = {
//    x._2 >= 2
//  }
//  out.close()

//  println(Jsoup.connect("http://stumail.xidian.edu.cn/user/?q=analyze&zid=50ed73163ad364cbb7219ce7a9cb6c8c").userAgent("Mozilla")
//  .cookies(Map(
//    "EMPHPSID" ->	"rcol6d2tgc9r1fmmnij8vs2300",
//    "empos" ->	"0",
//    "emLoginUser" -> "wli_021111%40stu.xidian.edu.cn")).data("client", "%7B%22ctime%22%3A1428722809%2C%22ctzon%22%3A8%2C%22ctyea%22%3A2015%2C%22ctmon%22%3A4%2C%22ctday%22%3A11%2C%22cthou%22%3A11%2C%22ctmin%22%3A26%2C%22ctsec%22%3A49%2C%22ctmse%22%3A724%2C%22cswid%22%3A1366%2C%22cshei%22%3A768%2C%22ua%22%3A%22Mozilla%2F5.0+(Windows+NT+6.3%3B+WOW64)+AppleWebKit%2F537.36+(KHTML%2C+like+Gecko)+Chrome%2F40.0.2214.93+Safari%2F537.36%22%2C%22bext%22%3A%22ierv%3A0%2C+iev%3A0%22%2C%22bdet%22%3A%22fla%3A16%2Ccoo%3A1%22%2C%22umod%22%3A%22listmail%22%7D").post().text())
}
