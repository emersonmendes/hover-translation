package br.com.emersonmendes.hovertranslation.actions

import br.com.emersonmendes.hovertranslation.services.TranslationService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VfsUtil

class ImportTranslationsAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val translationService = project.getService(TranslationService::class.java)

        val fileChooserDescriptor = buildChooserDescription("Select the CSV file to import from")
        val virtualFile = FileChooser.chooseFile(fileChooserDescriptor, project, null) ?: return
        val file = VfsUtil.virtualToIoFile(virtualFile)

        file.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(',').map { it.trim() }
                if (parts.size == 2) {
                    val ( key, value ) = parts
                    if (key.isNotBlank() && value.isNotBlank()) {
                        translationService.addKeyValue(key, value)
                    }
                }
            }
        }

        Messages.showInfoMessage(project, "Imported successfully!", "Success")
    }

    private fun buildChooserDescription(description: String): FileChooserDescriptor {
        val fileChooserDescriptor = FileChooserDescriptor(
            true,
            false,
            false,
            false,
            false,
            false
        )
        fileChooserDescriptor.description = description
        fileChooserDescriptor.withFileFilter { it.extension == "csv" }
        return fileChooserDescriptor
    }

}