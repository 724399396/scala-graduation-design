import scala.collection.SortedMap

object RelationShip extends App {
  import java.io.{File, PrintWriter}


//  DBOperation.allRelation().filter(persons => persons.nickName == "梁斌penny").map(persons =>
//    (persons, DBOperation.weighted(new TwoPerson(persons.nickName,persons.feedFrom)))).toList.sortWith(
//      (p1,p2) => p1._2 > p2._2).foreach( x =>
//        out.println("%s -> %s : %d".format(x._1.nickName,x._1.feedFrom,x._2)))
//  out.println()
//  DBOperation.allRelation().filter(persons => persons.feedFrom == "梁斌penny").map(persons =>
//    (persons, DBOperation.weighted(new TwoPerson(persons.nickName,persons.feedFrom)))).toList.sortWith(
//      (p1,p2) => p1._2 > p2._2).foreach( x =>
//    out.println("%s -> %s : %d".format(x._1.nickName,x._1.feedFrom,x._2)))
//  out.close
//
//  lazy val out2 = new PrintWriter(new File("relation-all.txt"))
//  DBOperation.allRelation().map(persons =>
//    (persons, DBOperation.weighted(new TwoPerson(persons.nickName,persons.feedFrom)))).toList.sortWith(
//      (p1,p2) => p1._2 > p2._2).foreach( x =>
//    out2.println("%s -> %s : %d".format(x._1.nickName,x._1.feedFrom,x._2)))
//  out2.close

  lazy val out = new PrintWriter(new File("relation-liangbin.txt"))
  val allRelation = DBOperation.allRelation()
  val map = collection.mutable.Map[TwoPerson,Int]()
  for(relation <- allRelation.filter(_.nickName == "梁斌penny"))
    map(relation) = map.getOrElse(relation, 0) + 1
  map.toList.sortWith((x,y) => x._2 > y._2).foreach(x =>
        out.println("%s -> %s : %d".format(x._1.nickName,x._1.feedFrom,x._2)))
  out.println()
  map.clear

  for(relation <- allRelation.filter(_.feedFrom == "梁斌penny"))
    map(relation) = map.getOrElse(relation, 0) + 1
  map.toList.sortWith((x,y) => x._2 > y._2).foreach(x =>
    out.println("%s -> %s : %d".format(x._1.nickName,x._1.feedFrom,x._2)))
  map.clear
  out.close()

  lazy val out2 = new PrintWriter(new File("relation-all.txt"))
  for(relation <- allRelation)
    map(relation) = map.getOrElse(relation, 0) + 1
  map.toList.sortWith((x,y) => x._2 > y._2).foreach(x =>
    out2.println("%s -> %s : %d".format(x._1.nickName,x._1.feedFrom,x._2)))
  map.clear
  out2.close()

}
