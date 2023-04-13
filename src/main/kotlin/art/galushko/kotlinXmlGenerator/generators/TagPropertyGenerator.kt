package art.galushko.kotlinXmlGenerator.generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.PropertySpec

class TagPropertyGenerator(
    private val name: String,
    private val tagType: String,
    private val packageName: String,
) : PropertyGenerator, BuilderGenerator {
    override fun getProperty() =
        PropertySpec.builder(name, ClassName(packageName, tagType + "Tag").copy(nullable = true))
            .mutable()
            .initializer("null")
            .build()

    override fun builderFunction() = listOf(
        FunSpec.builder(name)
            .addParameter(
                "provider", LambdaTypeName.get(
                    receiver = ClassName(packageName, "${tagType}Tag.${tagType}Builder"),
                    returnType = unitType,
                )
            )
            .addStatement(
                """return apply({
                |   tag.%N = %T.%N.invoke(provider)
                |})""".trimMargin(), name, ClassName(packageName, "${name}Tag"), "${name}Object"
            )
            .build()
    )
}