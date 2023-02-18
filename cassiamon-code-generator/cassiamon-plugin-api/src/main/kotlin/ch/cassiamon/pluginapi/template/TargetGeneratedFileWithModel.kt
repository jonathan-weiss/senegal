package ch.cassiamon.pluginapi.template

import ch.cassiamon.pluginapi.model.ConceptModelNode
import java.nio.file.Path

data class TargetGeneratedFileWithModel(
    val targetFile: Path,
    val model: List<ConceptModelNode>
)
