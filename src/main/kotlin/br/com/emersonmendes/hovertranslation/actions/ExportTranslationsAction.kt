package br.com.emersonmendes.hovertranslation.actions

import br.com.emersonmendes.hovertranslation.services.TranslationService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VfsUtil
import java.io.File

class ExportTranslationsAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project!!
        val translationService = project.getService(TranslationService::class.java)

        val fileChooserDescriptor = buildChooserDescription("Select a location to save the CSV file")
        val virtualFile = FileChooser.chooseFile(fileChooserDescriptor, project, null) ?: return
        val directory = VfsUtil.virtualToIoFile(virtualFile)

        File(directory, "translations.csv").bufferedWriter().use { writer ->
            translationService.state.map.forEach { (key, value) ->
                writer.write("$key,$value\n")
            }
        }

        Messages.showInfoMessage(project, "Exported successfully!", "Success")
    }

    private fun buildChooserDescription(description: String): FileChooserDescriptor {
        val fileChooserDescriptor = FileChooserDescriptor(
            false,
            true,
            false,
            false,
            false,
            false
        )
        fileChooserDescriptor.description = description
        return fileChooserDescriptor
    }

}