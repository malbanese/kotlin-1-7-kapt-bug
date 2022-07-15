package com.example.kotlinbug.gradle

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

class ExamplePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            val baseSourceDirectory =
                listOf("generated", "source", "examplePlugin").fold(project.buildDir, ::File)

            val appVariants =
                project.extensions.getByType(AppExtension::class.java).applicationVariants

            appVariants.all { variant ->
                val capitalizedName = variant.name.replaceFirstChar { it.uppercase() }
                val taskName = "generate${capitalizedName}ExamplePlugin"
                val task = project.tasks.create(taskName, ExampleTask::class.java) {
                    it.group = "ExamplePlugin"
                    it.sourceDirectory = File(baseSourceDirectory, variant.dirName)
                }

                variant.addJavaSourceFoldersToModel(task.sourceDirectory)
                variant.registerJavaGeneratingTask(task, task.sourceDirectory)
                variant.javaCompileProvider.get().dependsOn(task)
                val capitalizedVariant = variant.name.replaceFirstChar { it.uppercase() }

                (project.tasks.findByName("compile${capitalizedVariant}Kotlin") as? KotlinCompile)?.also { kotlinTask ->
                    kotlinTask.dependsOn(task)
                    kotlinTask.source(project.files(task.sourceDirectory))
                } ?: throw Exception("Could not find kotlin compile task for variant.")
            }
        }
    }
}