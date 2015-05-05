import java.io._

import scala.io.Source


object RelationShip extends App {

  //prepareData()
  /** * print friend
    */
  //  prepareMap("relation-all.txt", "relation-seri.back")
  val relations = readMap("relation-seri.back")
  //  println(relations)

  while (true) {
    println("准备OK")
    val queryName = readLine()
    relations.get(queryName) // 获取查询的人的好友关系列表
      .getOrElse({
      println("没有好友推荐:");
      List[WeightPerson]()
    }). //如果不存在 后续程序则不执行
      map(p => relations.get(p.nickName).getOrElse(List[WeightPerson]()) // 获取表中每一个人的好友关系
      .map(persons => (p, persons))).filter(!_.isEmpty)
      .flatten.sortWith((x, y) => x._2.weight + x._1.weight > y._2.weight + y._1.weight)
      .filter(x => !queryName.equals(x._2.nickName)).take(5).foreach {
      one => {
        println("你可能认识'%s',他来自于你的好友'%s',你们三人的关系度是%d(%d+%d):".format(one._2.nickName, one._1.nickName, one._2.weight + one._1.weight,
          one._1.weight, one._2.weight))
        println("\t你和你的好友的话题是 %s ".format(one._1.words.mkString(",")))
        println("\t你的两个好友的话题是 %s ".format(one._2.words.mkString(",")))
      }
    }
  }

  private def prepareMap(dataSource: String, outFile: String): Unit = {
    val source = scala.io.Source.fromFile(dataSource)("UTF-8")
    val relations = source.getLines().map(_.split("->"))
      .map { case Array(name, friend) => Some(name.trim -> WeightPerson(name, friend.trim))
    case _ => None
    }.filter(_.isDefined).map(_.get) // 将文本读入的格式映射为 人名 -> 他的朋友
      .foldLeft(Map[String, List[WeightPerson]]())((m, c) => m + (c._1.trim -> (m.getOrElse(c._1.trim, List[WeightPerson]()) :+ c._2)))

    val serialOut = new ObjectOutputStream(new FileOutputStream(outFile))
    serialOut.writeObject(relations)
    serialOut.close()
  }

  private def readMap(source: String): Map[String, List[WeightPerson]] = {
    val serialIn = new ObjectInputStream(new FileInputStream("relation-seri.back"))
    serialIn.readObject().asInstanceOf[Map[String, List[WeightPerson]]]
  }

  /** get relationship from database
    */
  def prepareData() {
    lazy val out = new PrintWriter(new File("relation-liangbin.txt"), "utf-8")
    val allRelation = DBOperation.allRelation()
    val map = collection.mutable.Map[TwoPerson, Int]()
    for (relation <- allRelation.filter(_.nickName == "梁斌penny"))
      map(relation) = map.getOrElse(relation, 0) + 1
    map.toList.sortWith((x, y) => x._2 > y._2).foreach(x =>
      out.println("%s -> %s : %d".format(x._1.nickName, x._1.feedFrom, x._2)))
    out.println()
    map.clear

    for (relation <- allRelation.filter(_.feedFrom == "梁斌penny"))
      map(relation) = map.getOrElse(relation, 0) + 1
    map.toList.sortWith((x, y) => x._2 > y._2).foreach(x =>
      out.println("%s -> %s : %d".format(x._1.nickName, x._1.feedFrom, x._2)))
    map.clear
    out.close()

    lazy val out2 = new PrintWriter(new File("relation-all.txt"), "utf-8")
    for (relation <- allRelation)
      map(relation) = map.getOrElse(relation, 0) + 1
    map.toList.sortWith((x, y) => x._2 > y._2).foreach(x =>
      out2.println("%s -> %s : %d".format(x._1.nickName, x._1.feedFrom, x._2)))
    map.clear
    out2.close()
  }
}

@SerialVersionUID(7767213528100030842L)
class WeightPerson(val nickName: String, val weight: Int, val words: Seq[String]) extends Serializable {
  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case x: WeightPerson => nickName.equals(x.nickName)
      case _ => false
    }
  }

  override def hashCode(): Int = {
    17 + 31 * nickName.hashCode
  }

  override def toString = s"WeightPerson($nickName, $weight, $words)"
}

object WeightPerson {
  lazy val stopWord = Source.fromFile("D:/work/guraduation/stopWord.txt", "UTF-8")
    .getLines().toSet

  private def readRelationWord(name: String, feedName: String) = {
    import org.ansj.splitWord.analysis.NlpAnalysis
    import org.ansj.splitWord.analysis.IndexAnalysis
    import collection.JavaConversions.asScalaBuffer
    println(name + " -> " + feedName)
    DBOperation.contactContent(new TwoPerson(name, feedName)) // 获取所有的转发内容
      .flatMap(x => NlpAnalysis.parse(x)).map(_.getName).filter(_.trim.length > 1) // 分词后取出内容长度大于1的
      .foldLeft(Map[String, Int]())((m, c) => m + (c -> (m.getOrElse(c, 0) + 1))) // 统计词频
      .toSeq.sortWith({ case ((_, count1), (_, count2)) => count1 > count2 }) // 按照词频排序
      .filterNot({ case (word, _) => stopWord.contains(word) }).map({
      case (name, count) => name
    }).take(5) // 过滤停用词
  }

  def apply(name: String, input: String): WeightPerson =
    input.split(":") match {
      case Array(friendName, weight) => new WeightPerson(friendName.trim, weight.trim.toInt, readRelationWord(name, friendName.trim))
    }
}


