import java.sql.Timestamp
import java.util

import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.{SqlSessionFactoryBuilder, SqlSession, SqlSessionFactory}

import scala.collection.mutable


@SerialVersionUID(442682285241964953L)
object DBOperation extends Serializable {
  val resource = "dbconf.xml"
  val reader = Resources.
    getResourceAsReader(resource)
  val sessionFactory: SqlSessionFactory =
    new SqlSessionFactoryBuilder().build(reader)
  val session: SqlSession = sessionFactory.openSession()

  // MicroBlog
  def save(microBlog: MicroBlog): Int = {
    if (!isBlogExist(microBlog)) {
      val statement =
        "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.save"
      val inserts = session.insert(statement, microBlog)
      session.commit()
      inserts
    } else 0
  }

  def isBlogExist(microBlog: MicroBlog): Boolean = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.isBlogExist"
    val count: Int = session.selectOne(statement, microBlog)
    session.commit()
    if (count > 0) true else false
  }

  // MicroBlogUser
  def saveFan(fan: MicroBlogUser): Int = {
    if (!isFanExist(fan)) {
      val statement =
        "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.saveFan"
      val inserts = session.insert(statement, fan)
      session.commit()
      inserts
    } else 0
  }

  def isFanExist(fan: MicroBlogUser): Boolean = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.isFanExist"
    val count: Int = session.selectOne(statement, fan)
    session.commit()
    if (count > 0) true else false
  }

  def saveFol(fol: MicroBlogUser): Int = {
    if (!isFolExist(fol)) {
      val statement =
        "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.saveFol"
      val inserts = session.insert(statement, fol)
      session.commit()
      inserts
    } else 0
  }

  def isFolExist(fol: MicroBlogUser): Boolean = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.isFolExist"
    val count: Int = session.selectOne(statement, fol)
    session.commit()
    if (count > 0) true else false
  }

  def follows(ofWho: String): List[MicroBlogUser] = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.follows"
    import scala.collection.JavaConversions.asScalaBuffer
    val users: mutable.Buffer[MicroBlogUser] = session.selectList(statement, ofWho).asInstanceOf[util.ArrayList[MicroBlogUser]]
    session.commit()
    var result = List[MicroBlogUser]()
    for (user <- users) result = result :+ user
    result
  }

  def allFollows(): List[MicroBlogUser] = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.allFollows"
    import scala.collection.JavaConversions.asScalaBuffer
    val users: mutable.Buffer[MicroBlogUser] = session.selectList(statement).asInstanceOf[util.ArrayList[MicroBlogUser]]
    session.commit()
    var result = List[MicroBlogUser]()
    for (user <- users) result = result :+ user
    result
  }

  def fans(ofWho: String): List[MicroBlogUser] = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.fans"
    import scala.collection.JavaConversions.asScalaBuffer
    val users: mutable.Buffer[MicroBlogUser] = session.selectList(statement, ofWho).asInstanceOf[util.ArrayList[MicroBlogUser]]
    session.commit()
    var result = List[MicroBlogUser]()
    for (user <- users) result = result :+ user
    result
  }

  def allFans(): List[MicroBlogUser] = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.allFans"
    import scala.collection.JavaConversions.asScalaBuffer
    val users: mutable.Buffer[MicroBlogUser] = session.selectList(statement).asInstanceOf[util.ArrayList[MicroBlogUser]]
    session.commit()
    var result = List[MicroBlogUser]()
    for (user <- users) result = result :+ user
    result
  }

  def saveSearch(microBlog: MicroBlog):Int = {
    if (!isSearchExist(microBlog)) {
      val statement =
        "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.saveSearch"
      val inserts = session.insert(statement, microBlog)
      session.commit()
      inserts
    } else 0
  }
  def isSearchExist(microBlog: MicroBlog): Boolean = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.isSearchExist"
    val count: Int = session.selectOne(statement, microBlog)
    session.commit()
    if (count > 0) true else false
  }
  def weighted(query: TwoPerson): Int = {
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.weighted"
    val weight: Int = session.selectOne(statement, query)
    session.commit()
    weight
  }

  def allRelation(): Set[TwoPerson] ={
    val statement =
      "com.qunar.liwei.graduation.weibo_crawler.weiboMapper.allRelation"
    import scala.collection.JavaConversions.asScalaBuffer
    val relations: mutable.Buffer[TwoPerson] = session.selectList(statement).asInstanceOf[java.util.ArrayList[TwoPerson]]
    session.commit()
    relations.toSet
  }


  def main(args: Array[String]): Unit = {
    //println(weighted(new TwoPerson("梁斌penny", "吴军博士")))
    println(allRelation().size)
  }
}
