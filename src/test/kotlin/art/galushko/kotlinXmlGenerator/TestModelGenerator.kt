package art.galushko.kotlinXmlGenerator

import art.galushko.kotlinXmlGenerator.testModel.TestMessageTag.TestMessageObject
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldMatchEach
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class TestModelGenerator : ModelGenerator("TestModel", "art.galushko.kotlinXmlGenerator.testModel")

fun main() {
    TestModelGenerator().generateModel()
}

class TestGeneratorTest {
    @Test
    fun modelGenerationShouldNotFail() {
        val testMessage = TestMessageObject {
            random_attribute { "attributeValue" }
            TestValue { "SimpleValue" }
            TestStrings(listOf("Str1" to "-", "Str2" to "-")) { it.first }
            ReusedList {
                Message { "WasReused" }
            }
            Anonymous {
                `class` { "class_value" }
                InnerAnonymous {
                    SomeAnonymous(listOf("123", "456")) {
                        ValueFromAnonymousList { it }
                    }
                }
            }
            SomeAnonymous {
                ValueFromAnonymousList { "ListValue" }
            }
        }
        assertSoftly(testMessage) {
            _namespace shouldBe "test"
            _attributes shouldContain ("random-attribute" to "attributeValue")
            TestValue shouldBe "SimpleValue"
            TestStrings shouldContainExactly listOf("Str1", "Str2")
            ReusedList shouldNotBe null
            ReusedList?.shouldHaveSingleElement {
                it._namespace == "reused" && it.Message == "WasReused"
            }
            Anonymous shouldNotBe null
            Anonymous?._namespace shouldBe "test"
            Anonymous?.`class` shouldBe "class_value"
            Anonymous?.InnerAnonymous shouldNotBe null
            Anonymous?.InnerAnonymous?.SomeAnonymous shouldNotBe null
            Anonymous?.InnerAnonymous?.SomeAnonymous?.size shouldBe 2
            Anonymous?.InnerAnonymous?.SomeAnonymous?.shouldMatchEach({ tag -> tag shouldNotBe null }, { tag -> tag shouldNotBe null })
            Anonymous?.InnerAnonymous?.SomeAnonymous?.get(0)?.ValueFromAnonymousList shouldBe "123"
            Anonymous?.InnerAnonymous?.SomeAnonymous?.get(1)?.ValueFromAnonymousList shouldBe "456"
            SomeAnonymous shouldNotBe null
            SomeAnonymous?.shouldHaveSingleElement { it.ValueFromAnonymousList == "ListValue" }
        }
    }
}