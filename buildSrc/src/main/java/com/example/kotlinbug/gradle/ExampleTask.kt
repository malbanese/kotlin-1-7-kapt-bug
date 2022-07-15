package com.example.kotlinbug.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class ExampleTask : DefaultTask() {
    private val EXAMPLE_CLASS = """
        package com.example.kotlinbug
        class SimpleDataSource {
            val value: Int = 1
        }
    """.trimIndent()

    @Internal
    lateinit var sourceDirectory: File

    @TaskAction
    fun generateKotlinFile() {
        File(sourceDirectory, "SimpleDataSource.kt").writeText(EXAMPLE_CLASS)
    }
}