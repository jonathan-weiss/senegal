Test template:
<#list rootTemplateModels as topLevelNode>

    Properties:
      conceptName: ${topLevelNode.conceptName}
      conceptIdentifier: ${topLevelNode.conceptIdentifier}
      className: ${topLevelNode.templateFacetValues.ClassName}
      className (direct access): ${topLevelNode.ClassName}
      packageName: ${topLevelNode.templateFacetValues.PackageName}
      packageName (direct access): ${topLevelNode.PackageName}
      Facets:
    <#list topLevelNode.templateFacetValues.allFacetNames as facetName>
        FacetName: ${facetName}: ${topLevelNode[facetName]}
    </#list>
      Children:
    <#list topLevelNode.allChildrenNodes as childNode>
        conceptName: ${childNode.conceptName}
        conceptIdentifier: ${childNode.conceptIdentifier}
    </#list>
</#list>
