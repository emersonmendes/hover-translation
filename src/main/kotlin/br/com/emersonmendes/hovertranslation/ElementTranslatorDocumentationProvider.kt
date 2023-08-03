package br.com.emersonmendes.hovertranslation

import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.KotlinDocumentationProvider
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.stubs.elements.KtNameReferenceExpressionElementType

class ElementTranslatorDocumentationProvider : DocumentationProvider {

    private val kotlinDocumentationProvider = KotlinDocumentationProvider()

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {

        val name = when(element){
            is KtNamedFunction -> element.name
            is KtProperty -> element.name
            is KtParameter -> element.name
            is KtNameReferenceExpressionElementType -> element.text
            else -> null
        }?: return null

        val baseDoc = kotlinDocumentationProvider.generateDoc(element, originalElement)?: ""

        return """
            <div class='definition'>
                <div><b>Original Name</b>: $name</div>
                <div><b>English Name</b>: $name</div>
                <br />
            </div>
        """.trimIndent() + baseDoc

    }

}