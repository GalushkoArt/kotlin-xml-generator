package art.galushko.kotlinXmlGenerator.testModel.TestMessage.Anonymous.InnerAnonymous

import art.galushko.kotlinXmlGenerator.model.Tag
import art.galushko.kotlinXmlGenerator.model.TagBuilder
import kotlin.String
import kotlin.Suppress
import kotlin.Unit

/**
 * This file was generated by art.galushko.kotlinXmlGenerator.generators.TagClassGenerator
 * Your changes will be lost
 */
@Suppress("RedundantVisibilityModifier", "PropertyName")
public class SomeAnonymousTag : Tag("test") {
  public var ValueFromAnonymousList: String? = null

  @Suppress("TestFunctionName", "MoveLambdaOutsideParentheses")
  public class SomeAnonymousBuilder(
    public override val tag: SomeAnonymousTag,
  ) : TagBuilder(tag) {
    public fun ValueFromAnonymousList(provider: () -> String?) = apply({
       tag.ValueFromAnonymousList = provider()
    })
  }

  public object SomeAnonymousObject {
    public operator fun invoke(initialize: SomeAnonymousBuilder.() -> Unit): SomeAnonymousTag =
        SomeAnonymousBuilder(SomeAnonymousTag()).apply(initialize).tag

    @Suppress("MoveLambdaOutsideParentheses")
    public fun <T> invokeWithParameter(parameter: T, initialize: SomeAnonymousBuilder.(T) -> Unit):
        SomeAnonymousTag = SomeAnonymousBuilder(SomeAnonymousTag()).also({ it.initialize(parameter)
        }).tag

    public fun build(provider: SomeAnonymousBuilder.() -> SomeAnonymousBuilder): SomeAnonymousTag =
        SomeAnonymousBuilder(SomeAnonymousTag()).provider().tag
  }
}