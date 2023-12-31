package br.com.emersonmendes.hovertranslation

import br.com.emersonmendes.hovertranslation.services.TranslationService
import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.jetbrains.kotlin.idea.KotlinDocumentationProvider
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes

class ElementTranslatorDocumentationProvider : DocumentationProvider {

    private val kotlinDocumentationProvider = KotlinDocumentationProvider()

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {

        val service = element.project.getService(TranslationService::class.java)
        val ( originalName, translatedName ) = element.name().translate(service)
        val baseDoc = kotlinDocumentationProvider.generateDoc(element, originalElement)?: ""

        return """
            <div class='definition'>
                <div><b>Original name</b>: $originalName</div>
                <div><b>Translated name</b>: $translatedName</div>
                <br />
            </div>
        """.trimIndent() + baseDoc

    }
}

private fun String.translate(service: TranslationService): Pair<String, String> {
    val splitName = this.splitToWords()
    val original = splitName.joinToString(" ")
    val translated = splitName.joinToString(" ") { service.getValue(it) }
    return Pair(original, translated)
}


private fun PsiElement.name(): String {

    val element = this

    if(element.elementType == KtStubElementTypes.REFERENCE_EXPRESSION){
        return element.text
    }

    return when (element) {
        is KtClass -> element.name!!
        is KtNamedFunction -> element.name!!
        is KtProperty -> element.name!!
        is KtParameter -> element.name!!
        else -> element.text
    }

}
