package art.galushko.kotlinXmlGenerator.testModel

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
public class ReusedTagTag : Tag("reused") {
  public var Message: String? = null

  @Suppress("TestFunctionName", "MoveLambdaOutsideParentheses")
  public class ReusedTagBuilder(
    public override val tag: ReusedTagTag,
  ) : TagBuilder(tag) {
    public fun Message(provider: () -> String?) = apply({
       tag.Message = provider()
    })
  }

  public object ReusedTagObject {
    public operator fun invoke(initialize: ReusedTagBuilder.() -> Unit): ReusedTagTag =
        ReusedTagBuilder(ReusedTagTag()).apply(initialize).tag

    @Suppress("MoveLambdaOutsideParentheses")
    public fun <T> invokeWithParameter(parameter: T, initialize: ReusedTagBuilder.(T) -> Unit):
        ReusedTagTag = ReusedTagBuilder(ReusedTagTag()).also({ it.initialize(parameter) }).tag

    public fun build(provider: ReusedTagBuilder.() -> ReusedTagBuilder): ReusedTagTag =
        ReusedTagBuilder(ReusedTagTag()).provider().tag
  }
}
