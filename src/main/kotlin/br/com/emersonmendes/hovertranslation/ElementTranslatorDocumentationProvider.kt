package br.com.emersonmendes.hovertranslation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.psi.PsiElement

class ElementTranslatorDocumentationProvider : AbstractDocumentationProvider() {

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? =
        super.generateDoc(element, originalElement)

}
//
////        val name = originalElement?.text
////
////        val doc = super.generateDoc(element, originalElement)!!
////
////        return """$doc\n
////        English name: $name
////        """.trimIndent()