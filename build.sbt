import org.jetbrains.sbtidea.Keys.IntelliJPlatform

name := "logic-over-code-ij"
organization := "com.logicovercode"
version := "0.0.1"

//https://github.com/JetBrains/intellij-scala/blob/7af383fc2af11276c5eb2f94cf814ef009e8a113/project/dependencies.scala#L4
scalaVersion := "2.13.2"
intellijPluginName in ThisBuild := "loc-ij"

//https://github.com/JetBrains/intellij-scala/blob/7af383fc2af11276c5eb2f94cf814ef009e8a113/project/dependencies.scala#L12
intellijBuild in ThisBuild := "211.6693.111"
intellijPlatform in ThisBuild := IntelliJPlatform.IdeaCommunity

/*Not sure about below settings
https://github.com/JetBrains/intellij-scala/blob/7af383fc2af11276c5eb2f94cf814ef009e8a113/build.sbt#L23
https://github.com/JetBrains/intellij-scala/blob/7af383fc2af11276c5eb2f94cf814ef009e8a113/build.sbt#L25

https://github.com/JetBrains/intellij-scala/blob/7af383fc2af11276c5eb2f94cf814ef009e8a113/project/dependencies.scala#L20
https://github.com/JetBrains/intellij-scala/blob/7af383fc2af11276c5eb2f94cf814ef009e8a113/project/dependencies.scala#L36

https://github.com/JetBrains/intellij-scala/blob/7af383fc2af11276c5eb2f94cf814ef009e8a113/build.sbt#L190
https://github.com/JetBrains/intellij-scala/blob/7af383fc2af11276c5eb2f94cf814ef009e8a113/build.sbt#L208
 */

intellijPlugins += "org.intellij.scala:2021.1.17".toPlugin
intellijDownloadSources := false
bundleScalaLibrary in ThisBuild := false

lazy val scalaIntellij = project.in(file(".")).enablePlugins(SbtIdeaPlugin)
