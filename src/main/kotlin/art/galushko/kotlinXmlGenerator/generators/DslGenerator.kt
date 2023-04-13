package art.galushko.kotlinXmlGenerator.generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.AnnotationSpec


open class DslGenerator(
    private val packageName: String,
    private val tagName: String,
) {
    fun generateDslObject() = TypeSpec.objectBuilder(ClassName(packageName, "${tagName}Object")).addFunctions(generateDslFunctions()).build()

    private fun generateDslFunctions(): Iterable<FunSpec> {
        val builderType = ClassName("", TagClassGenerator.getTagBuilderName(tagName))
        val tagType = ClassName("", TagClassGenerator.getTagClassName(tagName))
        return listOf(
            FunSpec.builder("invoke")
                .addModifiers(KModifier.OPERATOR)
                .addParameter(
                    "initialize", LambdaTypeName.get(
                        receiver = builderType,
                        returnType = unitType
                    )
                )
                .returns(tagType)
                .addStatement("return %T(%T()).apply(initialize).tag", builderType, tagType)
                .build(),
            FunSpec.builder("invokeWithParameter")
                .addTypeVariable(genericType)
                .addAnnotation(
                    AnnotationSpec.builder(Suppress::class).addMember("%S", "MoveLambdaOutsideParentheses").build()
                )
                .addParameter("parameter", genericType)
                .addParameter(
                    "initialize", LambdaTypeName.get(
                        receiver = builderType,
                        genericType,
                        returnType = unitType,
                    )
                )
                .returns(tagType)
                .addStatement("return %T(%T()).also({ it.initialize(parameter) }).tag", builderType, tagType)
                .build(),
            FunSpec.builder("build")
                .addParameter(
                    "provider", LambdaTypeName.get(
                        receiver = builderType,
                        returnType = builderType,
                    )
                )
                .returns(tagType)
                .addStatement("return %T(%T()).provider().tag", builderType, tagType)
                .build(),
        )
    }
}