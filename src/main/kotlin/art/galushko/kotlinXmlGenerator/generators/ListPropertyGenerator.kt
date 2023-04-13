package art.galushko.kotlinXmlGenerator.generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName

class ListPropertyGenerator(
    private val parent: TagClassGenerator,
    private val name: String,
    private val definition: Any,
) : PropertyGenerator, BuilderGenerator {
    override fun getProperty() = PropertySpec.builder(name, getParametrizedListType())
        .mutable()
        .initializer("null")
        .build()

    override fun builderFunction() = listOf(
        FunSpec.builder(name)
            .addParameter("provider", getLambdaForProvider())
            .addStatement(
                """return apply({
                |   tag.%N = (tag.%N ?: listOf()) + ${listAppender()}
                |})""".trimMargin(), name, name
            )
            .build(),
        FunSpec.builder(name)
            .addTypeVariable(genericType)
            .addParameter("list", listType.plusParameter(genericType))
            .addParameter("provider", getLambdaForGenericProvider())
            .addStatement(
                """return apply({
                |   tag.%N = (tag.%N ?: listOf()) + list.map(${listMapper()})
                |})""".trimMargin(), name, name
            )
            .build(),
        FunSpec.builder("Empty$name")
            .addStatement(
                """return apply({
                |   tag.%N = emptyList()
                |})""".trimMargin(), name
            )
            .build(),
    )

    private fun dslObjectReference() = "${TagClassGenerator.getTagClassName(getTagName())}.${getTagName()}Object"

    private fun listAppender() = if (definition == "String") "provider()"
    else "${dslObjectReference()}.invoke(provider)"
    private fun listMapper() = if (definition == "String") "provider"
    else "{ ${dslObjectReference()}.invokeWithParameter(it, provider) }"

    private fun builderReference() = "${TagClassGenerator.getTagClassName(getTagName())}.${TagClassGenerator.getTagBuilderName(getTagName())}"

    private fun getLambdaForProvider() = if (definition == "String") {
        LambdaTypeName.get(receiver = null, returnType = String::class.asTypeName())
    } else LambdaTypeName.get(
        receiver = ClassName(getPackageForTag(), builderReference()),
        returnType = unitType,
    )

    private fun getLambdaForGenericProvider() = if (definition == "String") {
        LambdaTypeName.get(receiver = null, genericType, returnType = String::class.asTypeName())
    } else LambdaTypeName.get(
        receiver = ClassName(getPackageForTag(), builderReference()),
        genericType,
        returnType = unitType,
    )

    private fun getParametrizedListType() = listType.plusParameter(getTypeByDefinition()).copy(nullable = true)

    private fun getTypeByDefinition() = when (definition) {
        "String" -> String::class.asTypeName()
        is String, is Map<*, *> -> ClassName(getPackageForTag(), TagClassGenerator.getTagClassName(getTagName()))
        else -> throw IllegalArgumentException("$definition is not allowed type for List! Allowed types are 'String', TagName, Map")
    }

    private fun getTagName() = if (definition is String) definition else name

    private fun getPackageForTag() = if (definition is Map<*, *>) parent.getPackageOfAnonymousTag() else parent.getBasePackage()
}