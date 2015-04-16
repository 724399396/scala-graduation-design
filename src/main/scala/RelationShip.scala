object RelationShip extends App {
  import java.io.{File, PrintWriter}

//  lazy val out = new PrintWriter(new File("relation-liangbin.txt"))
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

  lazy val out2 = new PrintWriter(new File("relation-all.txt"))
  DBOperation.allRelation().map(persons =>
    (persons, DBOperation.weighted(new TwoPerson(persons.nickName,persons.feedFrom)))).toList.sortWith(
      (p1,p2) => p1._2 > p2._2).foreach( x =>
    out2.println("%s -> %s : %d".format(x._1.nickName,x._1.feedFrom,x._2)))
  out2.close
}
