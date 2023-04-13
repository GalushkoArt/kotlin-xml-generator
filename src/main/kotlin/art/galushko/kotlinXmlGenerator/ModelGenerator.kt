package art.galushko.kotlinXmlGenerator

import art.galushko.kotlinXmlGenerator.generators.TagClassGenerator
import com.google.common.io.Resources
import com.squareup.kotlinpoet.FileSpec
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import java.nio.file.Path
import java.nio.file.Paths


@Suppress("UNCHECKED_CAST")
open class ModelGenerator(
    private val modelName: String,
    val basePackage: String,
    private val pathToWriteClasses: Path? = null,
) {
    private val reader = ObjectMapper()
    private val classDefinitions = ArrayDeque<TagClassGenerator>()

    /**
     * Generates the model by parsing the definition, checking it, defining tags, generating classes, and writing them to a file
     */
    fun generateModel() {
        println("Start parsing definition from $modelName.json")
        val definition = parseDefinition()
        println("Start checking definition")
        checkDefinition()
        val path = pathToWriteClasses ?: resolvePathToWriteGeneratedClasses()
        println("Path to write classes: $path")
        definition.filterNot { it.key.startsWith("$") || it.key.startsWith("@") }
            .forEach { (tag, tagDefinition) -> defineTag(tag, tagDefinition as Map<String, Any>, basePackage) }
        val fileSpecs = mutableListOf<FileSpec>()
        println("Start generate model")
        while (classDefinitions.isNotEmpty()) {
            val classGenerator = classDefinitions.removeFirst()
            fileSpecs.add(classGenerator.getFileSpec())
        }
        println("Start writing model classes to path: $path")
        fileSpecs.forEach { it.writeTo(path) }
        println("Done!")
    }

    /**
     * Resolves the path to write the generated classes to if pathToWriteClasses not passed
     */
    private fun resolvePathToWriteGeneratedClasses(): Path {
        var path = Paths.get("").toAbsolutePath()
        while (path.parent != null) {
            if (path.parent.endsWith("kotlin") || path.parent.endsWith("java")) break
            else path = path.parent
        }
        if (path.parent == null) {
            path = Paths.get("").toAbsolutePath().resolve("src/main/kotlin")
        }
        return path
    }

    private fun checkDefinition() {
        // TODO: Implement definition checking
    }

    private fun parseDefinition(): Map<String, Any> =
        reader.readValue(Resources.getResource("$modelName.json"), object : TypeReference<Map<String, Any>>() {})

    /**
     *  Defines a tag and adds it to the classDefinitions queue
     */
    fun defineTag(name: String, definition: Map<String, Any>, packageName: String = basePackage) {
        classDefinitions.add(TagClassGenerator(this, name, definition, packageName))
    }
}