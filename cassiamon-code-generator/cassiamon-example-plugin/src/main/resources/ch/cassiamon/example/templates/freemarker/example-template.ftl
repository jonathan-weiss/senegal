Example template:
<#list rootTemplateModels as topLevelNode>

    Properties: ${topLevelNode.conceptName}
<#--    <#list topLevelNode.properties as propertyKey, propertyValue>-->
<#--        ${propertyKey}: ${propertyValue}-->
<#--    </#list>-->

<#--    SubNodes:-->
<#--    <#list topLevelNode.childNodes as childNode>-->

<#--        Properties:-->
<#--        <#list childNode.properties as childPropertyKey, childPropertyValue>-->
<#--            ${childPropertyKey}: ${childPropertyValue}-->
<#--        </#list>-->
<#--    </#list>-->


</#list>
