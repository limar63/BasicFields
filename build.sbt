enablePlugins(ScalaJSPlugin)
name := "BasicFields"

version := "0.1"
libraryDependencies += "com.raquo" %%% "laminar" % "0.13.1"
scalacOptions ++= Seq("-deprecation")
scalaVersion := "2.13.6"
scalaJSUseMainModuleInitializer := true
