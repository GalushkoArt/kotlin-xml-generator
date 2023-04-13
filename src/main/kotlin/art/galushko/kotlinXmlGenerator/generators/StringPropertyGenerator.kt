package art.galushko.kotlinXmlGenerator.generators

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.PropertySpec

class StringPropertyGenerator(
    private val name: String,
) : PropertyGenerator, BuilderGenerator {
    override fun getProperty() = PropertySpec.builder(name, stringType)
        .mutable()
        .initializer("null")
        .build()

    override fun builderFunction() = listOf(
        FunSpec.builder(name)
            .addParameter("provider", LambdaTypeName.get(returnType = stringType))
            .addStatement(
                """return apply({
                |   tag.%N = provider()
                |})""".trimMargin(), name
            )
            .build()
    )
}