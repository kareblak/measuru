import sbt._
import sbt.Keys._
import scala.Some
import com.typesafe.startscript.StartScriptPlugin

object Build extends Build {

  lazy val root = Project(
    id = "brewpot",
    base = file("."),
    settings = Defaults.defaultSettings ++
      Seq(
        name                := Settings.name,
        description         := Settings.description,
        scalaVersion        := Settings.scalaVersion,
        organization        := Settings.organization,
        version             := Settings.version,
        homepage            := Settings.homepage,
        licenses            := Settings.licenses,
        pomExtra            := Settings.pomExtra,
        libraryDependencies ++= External.libraryDependencies,
        resolvers           ++= External.resolvers
    )
    ++ seq(StartScriptPlugin.startScriptForClassesSettings: _*)
  )

  object Settings {
    val name = "brewpot"
    val description = "Brewpot"
    val scalaVersion = "2.10.0"
    val organization = "org.brewpot"
    val version = "1.0-SNAPSHOT"
    val homepage = Some(new URL("http://github.com/brewpot/brewpot"))
    val startYear = Some(2012)
    val licenses = Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")))
  }

  object External {
    val resolvers = Seq(
      "sonatype repo" at "https://oss.sonatype.org/content/repositories/releases/",
      "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
    )
    val libraryDependencies = Seq(
      "net.databinder" %% "unfiltered" % "0.6.8",
      "net.databinder" %% "unfiltered-filter" % "0.6.8",
              "net.databinder" %% "unfiltered-netty" % "0.6.8"
    )
  }
}
