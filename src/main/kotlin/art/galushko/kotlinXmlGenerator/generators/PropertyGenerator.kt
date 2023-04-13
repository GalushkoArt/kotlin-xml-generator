package art.galushko.kotlinXmlGenerator.generators

import com.squareup.kotlinpoet.PropertySpec

interface PropertyGenerator: BuilderGenerator {
    fun getProperty(): PropertySpec
}