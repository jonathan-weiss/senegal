<#list topLevelNodes as topLevelNode>

    Properties:
    <#list topLevelNode.properties as propertyKey, propertyValue>
        ${propertyKey}: ${propertyValue}
    </#list>
    direct access: ${topLevelNode.myStringTwo}

    SubNodes:
    <#list topLevelNode.entityAttributes as entityAttribute>

        Properties:
        <#list entityAttribute.properties as entityAttributePropertyKey, entityAttributePropertyValue>
            ${entityAttributePropertyKey}: ${entityAttributePropertyValue}
        </#list>
    </#list>


</#list>
