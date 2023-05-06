package ch.cassiamon.api.template

import ch.cassiamon.api.model.ConceptModelNode
import java.nio.file.Path

data class TargetGeneratedFileWithModel(
    val targetFile: Path,
    val model: List<ConceptModelNode>
)
