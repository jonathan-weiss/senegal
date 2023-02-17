package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.model.graph.ModelGraph
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateNode
import ch.cassiamon.pluginapi.template.TemplateNodeBag
import ch.cassiamon.pluginapi.template.TemplateNodesProvider
import java.nio.file.Path

class TemplateNodesProviderDefaultImpl() : TemplateNodesProvider {
    override fun fetchAllTemplateNodes(): TemplateNodeBag {
        TODO("Not yet implemented")
    }

    override fun fetchTemplateNodes(conceptName: ConceptName): TemplateNodeBag {
        // TODO("Not yet implemented")
        return TemplateNodeBag(emptyList())

    }

    override fun targetGeneratedFileForEachTemplateNode(
        conceptName: ConceptName,
        generatedFileMapper: (templateNode: TemplateNode) -> Path
    ): Set<TargetGeneratedFileWithModel> {
        return fetchTemplateNodes(conceptName).nodes
            .map {templateNode -> x(templateNode, generatedFileMapper) }
            .toSet()
    }

    private fun x(templateNode: TemplateNode, generatedFileMapper: (templateNode: TemplateNode) -> Path): TargetGeneratedFileWithModel {
        val file = generatedFileMapper(templateNode)
        return TargetGeneratedFileWithModel(file, templateNode.asTemplateNodeBag)
    }
}
