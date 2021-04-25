package com.logicovercode.isplugin.wizfactory

import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.platform.{ProjectTemplate, ProjectTemplatesFactory}

class LogicOverCodeWizardFactory extends ProjectTemplatesFactory{
  override def getGroups: Array[String] = Array("Logic Over Code")

  override def createTemplates(s: String, wizardContext: WizardContext): Array[ProjectTemplate] = {
    if (wizardContext.isCreatingNewProject) {
      Array( new SbtExtendedTemplate )
    } else {
      Array.empty
    }
  }
}
