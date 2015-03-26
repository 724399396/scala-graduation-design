import java.io.FileWriter

/**
 * Created by liwei on 15-3-17.
 */
object FolsAndFans extends App {
  val fans = UrlGet.getFans("http://weibo.cn/pennyliang")
  val fols = UrlGet.getFetchFollows("http://weibo.cn/pennyliang")
  for (fan <- DBOperation.fans("梁斌penny")) {
      UrlGet.getFans(fan.baseUrl)
      println(fan)
  }
    for(fol <- DBOperation.follows("梁斌penny")) {
      UrlGet.getFollows(fol.baseUrl)
      println(fol)
    }

  //  import java.io.{File,Writer}
  //  lazy val out = new FileWriter(new File("fans.txt"))
  //  for (fan <- DBOperation.allFans())
  //    out.write(fan.getNickName + " " + fan.getOfWho + "\n")
}
