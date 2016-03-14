enablePlugins(ScalaJSPlugin)
workbenchSettings

name := "snafu"

version := "1.0"

scalaVersion := "2.11.7"

skip in packageJSDependencies := false

libraryDependencies ++= Seq(

  "org.scalaz"                        %%  "scalaz-core" % "7.2.1",
  "com.github.japgolly.scalajs-react" %%% "core"        % "0.10.4"

)


jsDependencies ++= Seq(

  "org.webjars.bower" % "react" % "0.14.3"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "0.14.3"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM"

)

scalacOptions ++= Seq("-feature")

bootSnippet := "snafu.ex.Main().main();"
updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

