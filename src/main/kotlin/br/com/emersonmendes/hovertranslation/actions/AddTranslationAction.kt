package br.com.emersonmendes.hovertranslation.actions

import br.com.emersonmendes.hovertranslation.services.TranslationService
import br.com.emersonmendes.hovertranslation.splitToWords
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.jetbrains.kotlin.lexer.KtTokens

class AddTranslationAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project ?: return
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val keys = editor.selectionModel.selectedText?.splitToWords()

        if(keys.isNullOrEmpty()){
            return
        }

        val service = project.getService(TranslationService::class.java)

        keys.forEach { key ->
            val value = Messages.showInputDialog(e.project, "Enter a value for: $key", "Add Value", null)
            if(!value.isNullOrEmpty()){
                service.addKeyValue(key, value)
            }
        }

        Messages.showInfoMessage(project, "Saved successfully!", "Success")

    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val editor = e.getData(PlatformDataKeys.EDITOR)
        val element = getPsiElementUnderCaret(project, editor)
        e.presentation.isEnabledAndVisible = element.elementType == KtTokens.IDENTIFIER
    }

    private fun getPsiElementUnderCaret(project: Project?, editor: Editor?): PsiElement? {
        if (project != null && editor != null) {
            val psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.document)
            val offset = editor.caretModel.offset
            return psiFile?.findElementAt(offset)
        }
        return null
    }

}