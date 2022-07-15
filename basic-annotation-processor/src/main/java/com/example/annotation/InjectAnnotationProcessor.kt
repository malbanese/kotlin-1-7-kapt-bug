package com.example.annotation

import javax.annotation.processing.*
import javax.inject.Inject
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic

@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("javax.inject.Inject")
class InjectAnnotationProcessor : AbstractProcessor() {
    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        roundEnv.getElementsAnnotatedWith(Inject::class.java).forEach {
            inspectElement(it)
        }
        return true
    }

    private fun log(message: String) = processingEnv.messager.printMessage(
        Diagnostic.Kind.NOTE,
        message
    )

    private fun inspectElement(element: Element) {
        log(
            """
            (name) ${element.simpleName}
            (kind) ${element.kind.name}
            (type) ${element.asType()}
            """.trimIndent()
        )
    }
}