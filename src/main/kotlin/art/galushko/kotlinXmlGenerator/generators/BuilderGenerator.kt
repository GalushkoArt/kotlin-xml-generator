package art.galushko.kotlinXmlGenerator.generators

import com.squareup.kotlinpoet.FunSpec

interface BuilderGenerator {
    fun builderFunction(): Iterable<FunSpec>
}