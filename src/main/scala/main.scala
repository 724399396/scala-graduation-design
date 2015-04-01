import java.io.{FileOutputStream, ObjectOutputStream, FileInputStream, ObjectInputStream}
import HtmlParse._

object main extends App {
  //    val users = UrlGet.getFollows("http://weibo.cn/pennyliang")
  //    lazy val out = new ObjectOutputStream(new FileOutputStream("users.se"));
  //    out.writeObject(users)
  //var readUsers: List[MicroBlogUser] = new ObjectInputStream(new FileInputStream("users.se")).readObject().asInstanceOf[List[MicroBlogUser]]
  //  var readUsers: List[MicroBlogUser] = List(new MicroBlogUser("http://weibo.cn/pennyliang", 200, 1))
  val subPage = 2
    var readUsers: List[MicroBlogUser] = DBOperation.follows("梁斌penny")
  readUsers = readUsers ++ DBOperation.fans("梁斌penny")
  while (readUsers.size != 0) {
    val first = readUsers.head
    readUsers = readUsers.tail
    val html = first.baseUrl + "?page=" + first.currentPage
    println(html + "  -- max: " + first.maxPage + " -- left: " + readUsers.size)
    val doc = getDoc(html)
    first.currentPage += 1
    getMicroBlog(doc).foreach { x => if (x != None) DBOperation.save(x.get)}
    if (first.currentPage <= first.maxPage) {
      if (first.nickName != first.ofWho && first.currentPage > subPage)
        println(first + " has been reach limit page")
      else readUsers = readUsers :+ first
    }
    else println(first + " has been finish")
    val out = new ObjectOutputStream(new FileOutputStream("users.se"))
    out.writeObject(readUsers)
    out.close()
  }
}
