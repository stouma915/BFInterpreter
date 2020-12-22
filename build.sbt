ThisBuild / version := "1.0.0"
ThisBuild / description := "Brainf*ck interpreter"
ThisBuild / scalaVersion := "2.13.3"

logLevel := Level.Debug

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.2" % "test",
)

lazy val root = (project in file("."))
  .settings(
    name := "BFInterpreter",
    assemblyOutputPath in assembly := baseDirectory.value / "target" / "build" / s"BFInterpreter-${version.value}.jar"
  )
