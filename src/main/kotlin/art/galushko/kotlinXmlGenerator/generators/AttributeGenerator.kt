package art.galushko.kotlinXmlGenerator.generators

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.asTypeName

class AttributeGenerator(
    private val name: String,
) : BuilderGenerator {
    override fun builderFunction() = listOf(
        FunSpec.builder(name.replace("[:\\-]".toRegex(), "_"))
            .addParameter("provider", LambdaTypeName.get(returnType = String::class.asTypeName()))
            .addStatement(
                """return apply({
                |   tag._attributes[%S] = provider()
                |})""".trimMargin(), name
            )
            .build()
    )
}