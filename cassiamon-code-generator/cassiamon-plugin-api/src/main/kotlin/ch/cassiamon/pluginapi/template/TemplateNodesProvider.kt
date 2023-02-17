package ch.cassiamon.pluginapi.template

import ch.cassiamon.pluginapi.ConceptName
import java.nio.file.Path

interface TemplateNodesProvider {

    fun fetchAllTemplateNodes(): TemplateNodeBag
    fun fetchTemplateNodes(conceptName: ConceptName): TemplateNodeBag

    fun targetGeneratedFileForEachTemplateNode(conceptName: ConceptName, generatedFileMapper: (templateNode: TemplateNode) -> Path): Set<TargetGeneratedFileWithModel>

}
