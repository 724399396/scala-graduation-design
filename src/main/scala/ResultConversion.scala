import java.io.{PrintWriter, File}
import java.util
import scala.io.Source
import scala.collection.mutable.{HashMap, Set}
import scala.collection.immutable.TreeMap
import scala.collection.JavaConversions.asScalaSet

// sort by double reverse
object DoubleReverse extends Ordering[Double] {
  def compare(a: Double, b: Double) = b compare a
}

object DoubleOrdering extends Ordering[(String, Double)] {
  def compare(a: (String, Double), b: (String, Double)) = b._2 compare a._2
}

object ResultConverse {
  val outDirectory = "resources//result//"

  def readConverseStorage(path: File): Set[(String, Double)] = {
    val m: HashMap[String, Double] = HashMap()
    val source = Source.fromFile(path).mkString
    val pattern = "([\\S]+)=([\\S]+)".r
    for (pattern(w, c) <- pattern.findAllIn(source)) {
      m(w) = (m.getOrElse(w, 0.0) + c.toDouble)
    }
    var lst: Set[(String, Double)] = new util.LinkedHashSet[(String, Double)]()
    var m2: TreeMap[Double, List[String]] = TreeMap()(DoubleReverse)
    m.foreach(a => m2 += (a._2 -> (m2.getOrElse(a._2, List()) :+ a._1)))
    m2.foreach(a => a._2.foreach(b => lst += (b -> a._1)))
    lst
  }

  def subFiles(dir: File, nameFilter: String): Iterator[File] = {
    val children = dir.listFiles().filter(_.isFile()).filter { x => x.getName.contains(nameFilter)}
    children.toIterator ++ dir.listFiles().filter(_.isDirectory()).flatMap { f => subFiles(f, nameFilter)}
  }

  def converse2Cloud(path: File): Unit = {
    val lst = readConverseStorage(path)
    val out = new PrintWriter(outDirectory + path.getParentFile.getName + ".txt")
    lst.take(50).foreach(e => out.println(e._1 + ":" + e._2))
    out.close()
  }

  def converse2Excel(path: File): Unit = {
    val lst = readConverseStorage(path)
    val out = new PrintWriter(outDirectory + path.getParentFile.getName + ".txt")
    lst.take(50).foreach(e => out.println(e._1 + "\t" + e._2))
    out.close()
  }

  def main(args: Array[String]) {
    for (f: File <- subFiles(new File("resources//data//n_a_filter"), "TFIDF"))
      converse2Excel(f)
  }
}