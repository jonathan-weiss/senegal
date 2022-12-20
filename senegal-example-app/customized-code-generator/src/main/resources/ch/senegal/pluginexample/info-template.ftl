<#list rootTemplateModels as topLevelNode>

    Level 1 - Properties:
    <#list topLevelNode.properties as propertyKey, propertyValue>
        ${propertyKey}: ${propertyValue}
    </#list>

    Level 1 - SubNodes:
    <#list topLevelNode.childNodes as childNode>

        Level 2 - Properties:
        <#list childNode.properties as childPropertyKey, childPropertyValue>
            ${childPropertyKey}: ${childPropertyValue}
        </#list>

        Level 2 - SubSubNodes:
        <#list childNode.childNodes as grandChildNode>

            Level 3 - Properties:
            <#list grandChildNode.properties as grandChildPropertyKey, grandChildPropertyValue>
                ${grandChildPropertyKey}: ${grandChildPropertyValue}
            </#list>
        </#list>
    </#list>


</#list>
