enablePlugins(ScalaJSPlugin)
workbenchSettings

name := "snafu"

version := "1.0"

scalaVersion := "2.11.7"

skip in packageJSDependencies := false

libraryDependencies ++= {
  val ScalaJsReactVersion = "0.10.4"
  val ScalaCssVersion     = "0.4.0"

  Seq(
    "org.scalaz"                        %%  "scalaz-core" % "7.2.1",
    "com.github.japgolly.scalajs-react" %%% "core"        % ScalaJsReactVersion,
    "com.github.japgolly.scalajs-react" %%% "extra"       % ScalaJsReactVersion,
    "com.github.japgolly.scalacss"      %%% "core"        % ScalaCssVersion,
    "com.github.japgolly.scalacss"      %%% "ext-react"   % ScalaCssVersion


  )
}


jsDependencies ++= {
  val ReactVersion = "0.14.3"

  Seq(
    "org.webjars.bower" % "react" % ReactVersion
      /        "react-with-addons.js"
      minified "react-with-addons.min.js"
      commonJSName "React",

    "org.webjars.bower" % "react" % ReactVersion
      /         "react-dom.js"
      minified  "react-dom.min.js"
      dependsOn "react-with-addons.js"
      commonJSName "ReactDOM"

  )
}

scalacOptions ++= Seq("-feature")

bootSnippet := "snafu.ex.Main().main();"
updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

