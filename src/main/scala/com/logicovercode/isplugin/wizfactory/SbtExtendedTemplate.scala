package com.logicovercode.isplugin.wizfactory

import com.intellij.ide.util.projectWizard.AbstractModuleBuilder
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.platform.ProjectTemplate

import javax.swing.Icon

class SbtExtendedTemplate extends ProjectTemplate{
  override def getName: String = "Fluent Sbt"

  override def getDescription: String = "fluent sbt based Scala project"

  override def getIcon: Icon = null

  override def createModuleBuilder(): AbstractModuleBuilder = new SbtExtendedBuilder

  override def validateSettings(): ValidationInfo = null
}
