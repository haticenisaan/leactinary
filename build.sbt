import com.typesafe.config.ConfigFactory

version :="1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .settings(sharedSettings)
  .settings(slick := slickCodeGenTask.value)
  .settings(sourceGenerators in Compile += slickCodeGenTask)
  .dependsOn(codegen)

lazy val codegen = project
  .settings(sharedSettings)
  .settings(libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.2.1")

lazy val id="slick-codegen-example"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies ++= Seq(ehcache, ws, specs2 % Test, guice)

lazy val sharedSettings = Seq(
  scalaVersion := "2.12.2",
  scalacOptions := Seq("-feature", "-unchecked", "-deprecation"),
  libraryDependencies ++= List(
    "com.typesafe.play" %% "play-slick" % "3.0.2",
    "com.typesafe.play" %% "play-slick-evolutions" % "3.0.2",
    "com.github.tminglei" %% "slick-pg" % "0.15.3",
    "org.postgresql" % "postgresql" % "42.1.4"
  )
)

val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map {(dir,cp,r,s) =>
  val outputDir = (dir/"slick").getPath
  val url = conf.getString("slick.dbs.default.db.url")
  val jdbcDriver = conf.getString("slick.dbs.default.db.driver")
  val slickDriver = conf.getString("slick.dbs.default.db.driver").dropRight(1)
  val pkg = "com.codegen"
  val user= conf.getString("slick.dbs.default.db.username")
  val password= conf.getString("slick.dbs.default.db.password")
  val fname= outputDir + s"/$pkg/Tables.scala"
    toError(r.run(s"$pkg.CodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg,user,password), s.log))
  Seq(file(fname))
}
