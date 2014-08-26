import sbt._

object Resolvers {
  val local =       Resolver.defaultLocal
  val local_maven = "Local Maven repo"   at "file://"+Path.userHome.absolutePath+"/.m2/repository"
  val maven =       "MAVEN repo"         at "http://repo1.maven.org/maven2"
  val sonatype =    "sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/"
  val sona_snap =   "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
  val typsafe =     "typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/"
  val spary =       "spray repo"         at "http://repo.spray.io/"
  val spary2 =      "Spray repo second"  at "http://repo.spray.cc/"
  val akka =        "Akka repo"          at "http://repo.akka.io/releases/"
  val scala_tools = "Scala Tools"        at "http://scala-tools.org/repo-releases/"
  val excilys =     "Excilys"            at "http://repository.excilys.com/content/groups/public"
  val excilys_snap ="Excilys Snapshot"   at "http://repository-gatling.forge.cloudbees.com/snapshot"
  val spray_nightly="spray nightlies"    at "http://nightlies.spray.io"

  val myResolvers = Seq (
    local,
    local_maven,
    maven,
    sonatype,
    sona_snap,
    typsafe,
    spary,
    spary2,
    akka,
    scala_tools,
    excilys_snap,
    spray_nightly
  )
}
