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
                    @BeanProperty var maxPage: Int, var currentPage: Int = 1) extends Serializable {
  def this() {
    this(null, null, null, 0)
  }

  override def toString = "url: " + baseUrl + ", nickName: " + nickName + ", ofWho: " + ofWho
}

@SerialVersionUID(20150416L)
class TwoPerson(@BeanProperty var nickName: String,
                @BeanProperty var feedFrom: String) extends Serializable{
  def this() { this(null,null) }


  override def toString = s"TwoPerson($nickName, $feedFrom)"

  def canEqual(other: Any): Boolean = other.isInstanceOf[TwoPerson]

  override def equals(other: Any): Boolean = other match {
    case that: TwoPerson =>
      (that canEqual this) &&
        nickName == that.nickName &&
        feedFrom == that.feedFrom
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(nickName, feedFrom)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}