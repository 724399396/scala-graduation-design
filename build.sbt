name := "guraduation"

version := "1.0"

scalaVersion := "2.10.4"

//resolvers += "Nexus" at "http://localhost:8081/nexus/content/groups/public/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "mvn-repo" at "http://maven.ansj.org/"

libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2"

libraryDependencies += "org.mybatis" % "mybatis" % "3.2.3"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.13"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.3.9"

libraryDependencies +=  Seq("org.ansj" % "ansj_seg" % "2.0.8")