import scala.beans.BeanProperty

class MicroBlog(@BeanProperty val nickName: String, @BeanProperty val `type`: String,
                @BeanProperty val microBlogContent: String, @BeanProperty val mediaBox: String, @BeanProperty val feedFrom: String,
                @BeanProperty val rePostContent: String, @BeanProperty val time: java.sql.Timestamp,
                @BeanProperty val keyword: String = null) {
  override def toString = "nickName: " + nickName + ", microBlog: " + microBlogContent +
    "rePostContent: " + rePostContent + "time: " + time + "keyword: " + keyword
}

@SerialVersionUID(20150206L)
class MicroBlogUser(@BeanProperty val baseUrl: String, @BeanProperty val nickName: String,
                    @BeanProperty val ofWho: String,
                    @BeanProperty val maxPage: Int, var currentPage: Int = 1) extends Serializable {
  def this() {
    this(null, null, null, 1)
  }

  override def toString = "url: " + baseUrl + ", nickName: " + nickName + ", ofWho: " + ofWho
}