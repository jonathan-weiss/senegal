Test template:
<#list rootTemplateModels as topLevelNode>

    Properties:
      conceptName: ${topLevelNode.conceptName}
      conceptIdentifier: ${topLevelNode.conceptIdentifier}
      className: ${topLevelNode.templateFacetValues.className}
      packageName: ${topLevelNode.templateFacetValues.packageName}
</#list>
