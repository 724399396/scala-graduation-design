import org.jsoup.Jsoup

/**
 * Created by liwei on 15-3-17.
 */
object test extends App {
  import collection.JavaConversions.mapAsJavaMap
  val testString = "很多普通美国家庭来"
//  println(Jsoup.connect("http://weibo.cn/pennyliang").timeout(1000).userAgent("Mozilla")
//    .cookies(CookieKeeper.cookie1).get().text().contains(testString))
//  println(Jsoup.connect("http://weibo.cn/pennyliang").timeout(1000).userAgent("Mozilla")
//    .cookies(CookieKeeper.cookie2).get().text().contains(testString))
//  println(Jsoup.connect("http://weibo.cn/pennyliang").timeout(1000).userAgent("Mozilla")
//    .cookies(CookieKeeper.cookie3).get().text().contains(testString))
//  println(Jsoup.connect("http://weibo.cn/pennyliang").timeout(1000).userAgent("Mozilla")
//    .cookies(CookieKeeper.cookie4).get().text().contains(testString))
//  println(Jsoup.connect("http://weibo.cn/pennyliang").timeout(1000).userAgent("Mozilla")
//    .cookies(CookieKeeper.cookie5).get().text().contains(testString))

    import java.io.{File,FileWriter}
    lazy val out = new FileWriter(new File("fans.txt"))
  println(DBOperation.follows("梁斌penny").size)
    for (fan <- DBOperation.follows("梁斌penny"))
      out.write(fan.getNickName + " " + fan.getOfWho + " "
        + DBOperation.weighted(new TwoPerson(fan.getNickName, fan.getOfWho)) + "\n")
  out.close()
}
