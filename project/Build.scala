import sbt._
import sbt.Keys._
import scala.Some
import com.typesafe.startscript.StartScriptPlugin

object Build extends Build {

  lazy val root = Project(
    id = "measuru",
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
        libraryDependencies ++= External.libraryDependencies,
        resolvers           ++= External.resolvers
    )
    ++ seq(StartScriptPlugin.startScriptForClassesSettings: _*)
  )

  object Settings {
    val name = "measuru"
    val description = "Measure everything!"
    val scalaVersion = "2.10.2"
    val organization = "com.measuru"
    val version = "1.0-SNAPSHOT"
    val homepage = Some(new URL("http://github.com/kareblak/measuru"))
    val startYear = Some(2013)
    val licenses = Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")))
  }

  object External {
    val resolvers = Seq(
      "sonatype repo" at "https://oss.sonatype.org/content/repositories/releases/",
      "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
    )
    val libraryDependencies = Seq(
      "net.databinder" %% "unfiltered"            % "0.6.8",
      "net.databinder" %% "unfiltered-filter"     % "0.6.8",
      "net.databinder" %% "unfiltered-jetty"      % "0.6.8",
      "net.databinder" %% "unfiltered-directives" % "0.6.8",
      "net.databinder" %% "dispatch-oauth"        % "0.8.9",
      "org.mongodb"    %% "casbah"                % "2.6.2",
      "com.jteigen"    %% "linx"                  % "0.1"
    )
  }
}
