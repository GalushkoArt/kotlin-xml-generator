package art.galushko.kotlinXmlGenerator.model

@XmlModel
abstract class Tag(
    val _namespace: String,
    val _attributes: MutableMap<String, String> = mutableMapOf()
)