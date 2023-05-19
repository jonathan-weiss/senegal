Example template:

<#list rootTemplateModels as topLevelNode>

    Properties:
    conceptName: ${topLevelNode.conceptName}
    conceptIdentifier: ${topLevelNode.conceptIdentifier}
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
