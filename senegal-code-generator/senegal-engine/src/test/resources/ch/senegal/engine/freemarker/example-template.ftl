package ${packageName}
// THIS IS GENERATED SOURCE CODE. DO NOT MODIFY. CHANGES WILL BE LOST!

// mySpecialDelegate: ${mySpecialDelegate.foo.bar}

data class ${className}(
<#list classFields as field>
    val ${field.name}: ${field.type}${field.nullable?string('?', '')}
</#list>

)
