package art.galushko.kotlinXmlGenerator.testModel.TestMessage.Anonymous

import art.galushko.kotlinXmlGenerator.model.Tag
import art.galushko.kotlinXmlGenerator.model.TagBuilder
import art.galushko.kotlinXmlGenerator.testModel.TestMessage.Anonymous.InnerAnonymous.SomeAnonymousTag
import art.galushko.kotlinXmlGenerator.testModel.TestMessage.Anonymous.InnerAnonymous.SomeAnonymousTag.SomeAnonymousBuilder
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List

/**
 * This file was generated by art.galushko.kotlinXmlGenerator.generators.TagClassGenerator
 * Your changes will be lost
 */
@Suppress("RedundantVisibilityModifier", "PropertyName")
public class InnerAnonymousTag : Tag("test") {
  public var SomeAnonymous: List<SomeAnonymousTag>? = null

  @Suppress("TestFunctionName", "MoveLambdaOutsideParentheses")
  public class InnerAnonymousBuilder(
    public override val tag: InnerAnonymousTag,
  ) : TagBuilder(tag) {
    public fun SomeAnonymous(provider: SomeAnonymousTag.SomeAnonymousBuilder.() -> Unit) = apply({
       tag.SomeAnonymous = (tag.SomeAnonymous ?: listOf()) +
        SomeAnonymousTag.SomeAnonymousObject.invoke(provider)
    })

    public fun <T> SomeAnonymous(list: List<T>,
        provider: SomeAnonymousTag.SomeAnonymousBuilder.(T) -> Unit) = apply({
       tag.SomeAnonymous = (tag.SomeAnonymous ?: listOf()) + list.map({
        SomeAnonymousTag.SomeAnonymousObject.invokeWithParameter(it, provider) })
    })

    public fun EmptySomeAnonymous() = apply({
       tag.SomeAnonymous = emptyList()
    })
  }

  public object InnerAnonymousObject {
    public operator fun invoke(initialize: InnerAnonymousBuilder.() -> Unit): InnerAnonymousTag =
        InnerAnonymousBuilder(InnerAnonymousTag()).apply(initialize).tag

    @Suppress("MoveLambdaOutsideParentheses")
    public fun <T> invokeWithParameter(parameter: T, initialize: InnerAnonymousBuilder.(T) -> Unit):
        InnerAnonymousTag = InnerAnonymousBuilder(InnerAnonymousTag()).also({
        it.initialize(parameter) }).tag

    public fun build(provider: InnerAnonymousBuilder.() -> InnerAnonymousBuilder): InnerAnonymousTag
        = InnerAnonymousBuilder(InnerAnonymousTag()).provider().tag
  }
}
