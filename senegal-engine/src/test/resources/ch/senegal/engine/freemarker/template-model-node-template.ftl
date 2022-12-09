<#list topLevelNodes as topLevelNode>

    Properties:
    <#list topLevelNode.properties as propertyKey, propertyValue>
        ${propertyKey}: ${propertyValue}
    </#list>
    direct access: ${topLevelNode.TestKotlinModelClassname}

    SubNodes:
    <#list topLevelNode.childNodes as childNode>

        Properties:
        <#list childNode.properties as childPropertyKey, childPropertyValue>
            ${childPropertyKey}: ${childPropertyValue}
        </#list>
    </#list>


</#list>
