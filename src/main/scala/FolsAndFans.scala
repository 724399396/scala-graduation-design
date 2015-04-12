import java.io.FileWriter

/**
 * Created by liwei on 15-3-17.
 */
object FolsAndFans extends App {

  import scala.util.control.Breaks._

  breakable {
    val fans = UrlGet.getFetchFans("http://weibo.cn/pennyliang")
    for (fan <- fans) {
      if (DBOperation.isFanExist(fan)) break
      println("第一级： " + fan + ": " + DBOperation.isFanExist(fan))
      DBOperation.saveFan(fan)
    }
  }
  println("第一级的fan完成")
  breakable {
    for (fol <- UrlGet.getFetchFollows("http://weibo.cn/pennyliang")) {
      if (DBOperation.isFolExist(fol)) break
      println("主 Fol " + fol + ": " + DBOperation.isFolExist(fol))
      DBOperation.saveFol(fol)
    }
  }
  println("第一级的follow完成")
  for (fan <- DBOperation.fans("梁斌penny")) {
    breakable {
      println(fan)
      for (subFan <- UrlGet.getFans(fan.baseUrl)) {
        if (DBOperation.isFanExist(subFan)) break
        DBOperation.saveFan(subFan)
        println(subFan)
      }
    }
  }
  println("第二级的fan完成")
  for (fol <- DBOperation.follows("梁斌penny")) {
    breakable {
      println(fol)
      for (subFol <- UrlGet.getFollows(fol.baseUrl)) {
        if (DBOperation.isFolExist(subFol)) break
        DBOperation.saveFol(subFol)
        println(subFol)
      }
    }
  }
  println("第二级的follow完成")

  //  import java.io.{File,Writer}
  //  lazy val out = new FileWriter(new File("fans.txt"))
  //  for (fan <- DBOperation.allFans())
  //    out.write(fan.getNickName + " " + fan.getOfWho + "\n")
}
