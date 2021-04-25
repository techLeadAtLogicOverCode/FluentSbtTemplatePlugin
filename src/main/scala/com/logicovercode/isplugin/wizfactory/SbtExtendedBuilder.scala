package com.logicovercode.isplugin.wizfactory

import com.intellij.openapi.module.{ModifiableModuleModel, Module}
import com.intellij.openapi.util.io
import org.jetbrains.annotations.NonNls
import org.jetbrains.sbt.RichFile
import org.jetbrains.sbt.Sbt.{BuildFile, PluginsFile, ProjectDirectory, PropertiesFile}
import org.jetbrains.sbt.project.template.SbtModuleBuilder

import java.io.File

class SbtExtendedBuilder extends SbtModuleBuilder with ReadPrivateField {

  override def createModule(moduleModel: ModifiableModuleModel): Module = {
    new File(getModuleFileDirectory) match {
      case root if root.exists() =>
        val selectionsFieldName = "org$jetbrains$sbt$project$template$SbtModuleBuilder$$selections"
        val selectionsRef = readSuperPrivateField(this, selectionsFieldName)
        println(s"selection field from SbtModuleBuilder >$selectionsRef<")

        val sbtVersion = readPrivateField(selectionsRef, "sbtVersion").asInstanceOf[String]
        println(s"sbt version from Selections >$sbtVersion<")

        val scalaVersion = readPrivateField(selectionsRef, "scalaVersion").asInstanceOf[String]
        println(s"scala version from Selections >$scalaVersion<")

        val sbtPlugins = Option(readPrivateField(selectionsRef, "sbtPlugins").asInstanceOf[String])
        println(s"sbt plugins from Selections >$sbtPlugins<")

        val resolveClassifiers = readPrivateField(selectionsRef, "resolveClassifiers").asInstanceOf[Boolean]
        println(s"resolve classifiers from Selections >$resolveClassifiers<")

        val resolveSbtClassifiers = readPrivateField(selectionsRef, "resolveSbtClassifiers").asInstanceOf[Boolean]
        println(s"resolve sbt classifiers from Selections >$resolveSbtClassifiers<")

        val packagePrefix = Option(readPrivateField(selectionsRef, "sbtPlugins").asInstanceOf[String])
        println(s"package prefix Selections >$packagePrefix<")

        val settings = getExternalProjectSettings
        settings.setResolveClassifiers(resolveClassifiers)
        settings.setResolveSbtClassifiers(resolveSbtClassifiers)

        createFluentProjectTemplateIn(root, getName, scalaVersion, sbtVersion, sbtPlugins, packagePrefix)

        setModuleFilePath(updateModuleFilePath(getModuleFilePath))
      case _ =>
    }

    super.createModule(moduleModel)
  }

  private def createFluentProjectTemplateIn(root: File,
                                            @NonNls name: String,
                                            @NonNls scalaVersion: String,
                                            @NonNls sbtVersion: String,
                                            @NonNls sbtPlugins: Option[String],
                                            packagePrefix: Option[String]): Unit = {


    val buildFile = root / BuildFile
    val projectDir = root / ProjectDirectory

    if (buildFile.createNewFile() && projectDir.mkdir()) {
      (root / "app").mkdirs()
      (root / "app-spec").mkdirs()

      import io.FileUtil.writeToFile

      writeToFile(
        buildFile,
        s"""val sbtBuild = SbtBuild("your-organization", "your-project", "0.0.1")
           |  .sourceDirectories("app")
           |  .testSourceDirectories("app-spec")
           |  .testDependencies( scala_test() )
           |  .moduleScalaVersion("2.11.7")
           |
           |val yourProject = ( project in file(".") )
           |  .settings( sbtBuild.settings )
           |""".stripMargin +
          packagePrefix.map(prefix =>
            s"""
               |idePackagePrefix := Some("$prefix")
               |""".stripMargin).getOrElse("")
      )
      writeToFile(
        projectDir / PropertiesFile,
        "sbt.version = " + sbtVersion
      )
      if (packagePrefix.isDefined) {
        writeToFile(
          projectDir / PluginsFile,
          """addSbtPlugin("org.jetbrains" % "sbt-ide-settings" % "1.1.0")
            |""".stripMargin
        )
      }

      val plugins =
        """
          |addSbtPlugin("org.logicovercode" %% "fluent-style-sbt" % "0.0.517")
          |""".stripMargin + sbtPlugins.getOrElse("")

      writeToFile(
        projectDir / PluginsFile,
        plugins
      )
    }
  }
}
