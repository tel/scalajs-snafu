logLevel := Level.Warn

resolvers += "spray repo" at "http://repo.spray.io"
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.7")
