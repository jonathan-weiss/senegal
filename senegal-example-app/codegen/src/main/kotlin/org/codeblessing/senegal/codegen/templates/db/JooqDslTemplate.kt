package org.codeblessing.senegal.codegen.templates.db

import org.codeblessing.senegal.sharedservice.tx.Transactional
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.forEach
import org.codeblessing.senegal.codegen.toolbox.template.template

object JooqDslTemplate {

    fun fillTemplate(dbTable: DbTable): String {
        return template {
            +"package ${dbTable.jooqDslPackage}"
            +""
            +"import org.jooq.*"
            +"import org.jooq.impl.*"
            +"import java.util.*"
            +""
            +"class ${dbTable.jooqDslName} private constructor("
            indented {
                +"alias: Name = DSL.unquotedName(\"${dbTable.tableName}\"),"
                +"aliased: Table<Record>? = null"
            }
            +") : TableImpl<Record>(alias, null, aliased, null) {"
            +""
            +"    val ${dbTable.primaryKeyJooqFieldName}: Field<${dbTable.primaryKeyJooqFieldType}>"
            indented {
            +"    ${forEach(dbTable.tableFields()) { dbField ->""
                +""
                +"    val ${dbField.jooqFieldName}: Field<${dbField.jooqFieldType}>"
                +" } }"
            }
+""
            +"    override fun `as`(alias: String): ${dbTable.jooqDslName} {"
            +"        return ${dbTable.jooqDslName}(DSL.quotedName(alias))"
            +"    }"
+""
            +"    private constructor(alias: Name) : this(alias, TABLE)"
+""
            +"    init {"
            +"        ${dbTable.primaryKeyJooqFieldName} = createField(DSL.unquotedName("${dbTable.primaryKeyColumnName}"), DefaultDataType.getDataType(SQLDialect.DEFAULT, ${dbTable.primaryKeyJooqFieldType}::class.java), this)"
            +"        ${forEach(dbTable.tableFields()) { dbField ->""
                    +""    """"
            +"        ${dbField.jooqFieldName} = createField(DSL.unquotedName("${dbField.columnName}"), DefaultDataType.getDataType(SQLDialect.DEFAULT, ${dbField.jooqFieldType}::class.java), this)"
            +"    """ } }"
            +"    }"
+""
            +"    companion object {"
            +"        val TABLE = ${dbTable.jooqDslName}()"
            +"    }"
            +"}"

        }

        return template {
            +"package ${facadeInterface.interfacePackageName()}"
            +""
            +"import javax.annotation.Generated"
            +"import ch.ergon.zebra.orca.app.shared.service.stereotypes.OrcaAppFacadeSkeleton"
            +"import ch.ergon.zebra.orca.lib.shared.service.context.bizo.${kotlinRestResource.orcaContextClassName()}"
            include(KotlinImportTemplateUtil.importsTemplate(allImports), startWithBlankLineIfNotEmpty = true)
            +""
            +"/**"
            +" * Achtung: Dies ist eine generierte Klasse, Änderungen an dieser Klasse gehen verloren!"
            +" *"
            +" * Die Implementierung dieser Klasse ist ein CDI-Bean und wird aus dem REST Layer"
            +" * durch ein REST Call aufgerufen. Sie führt die Business-Logik des REST Calls"
            +" * unter ${restResource.basePathOrDefault()} aus."
            +" *"
            +" * Die REST-Resource wird durch"
            +" * [${kotlinRestResource.kotlinResourceFullQualifiedName()}]."
            +" * implementiert."
            +" *"
            +" * Für die Prüfung der Berechtigungen (Validator) wird die Implementierung vom Interface"
            +" * [${kotlinRestValidator.interfaceFullQualifiedName()}]."
            +" * verwendet."
            +" */"
            +"@Generated(value = [\"${OrcaCodegenDomainUnit::class.qualifiedName}\"])"
            +"@OrcaAppFacadeSkeleton"
            +"interface ${facadeInterface.interfaceName()} {"
            indented {
                forEach(restResource.operations, startWithBlankLineIfNotEmpty = true, separateWithBlankLineIfNotEmpty = true) { restOperation ->
                    val facadeOperation = restOperation.toKotlinFacadeInterfaceOperation()
                    +"/*"
                    +" * Methode '${facadeOperation.facadeMethodName()}' ($authenticationInfo)"
                    +" */"
                    +"fun ${facadeOperation.facadeMethodName()}("
                    indented {
                        +"orcaCtx: ${kotlinRestResource.orcaContextClassName()},"
                        forEach(restOperation.operationRequestPathAndQueryParams()) {
                            val requestParam = it.toKotlinRestResourceOperationRequestParam().kotlinVariableNameForParamName()
                            val requestParamType = it.type.toKotlinBizoType().kotlinSimpleTypeNameWithNullability()
                            +"$requestParam: $requestParamType,"
                        }
                        restOperation.operationRequestPayloadType()?.let {
                            +"${it.requestPayloadVariableName()} : ${it.payloadType()},"
                        }
                    }
                    +")${restOperation.operationResponsePayloadType()?.let { ": ${it.payloadType()}" } ?: ""}"
                }
            }
            +"}"
        }.build()


//        return """
//            +"package ${dbTable.jooqDslPackage}"
//            +""
//            +"import org.jooq.*"
//            +"import org.jooq.impl.*"
//            +"import java.util.*"
//            +""
//            +"class ${dbTable.jooqDslName} private constructor("
//            +"    alias: Name = DSL.unquotedName("${dbTable.tableName}"),"
//            +"    aliased: Table<Record>? = null"
//            +") : TableImpl<Record>(alias, null, aliased, null) {"
//            +""
//            +"    val ${dbTable.primaryKeyJooqFieldName}: Field<${dbTable.primaryKeyJooqFieldType}>"
//            +"    ${forEach(dbTable.tableFields()) { dbField ->""
//            +""""""
//            +"    val ${dbField.jooqFieldName}: Field<${dbField.jooqFieldType}>"
//            +""" } }"
//+""
//            +"    override fun `as`(alias: String): ${dbTable.jooqDslName} {"
//            +"        return ${dbTable.jooqDslName}(DSL.quotedName(alias))"
//            +"    }"
//+""
//            +"    private constructor(alias: Name) : this(alias, TABLE)"
//+""
//            +"    init {"
//            +"        ${dbTable.primaryKeyJooqFieldName} = createField(DSL.unquotedName("${dbTable.primaryKeyColumnName}"), DefaultDataType.getDataType(SQLDialect.DEFAULT, ${dbTable.primaryKeyJooqFieldType}::class.java), this)"
//            +"        ${forEach(dbTable.tableFields()) { dbField ->""
//            +""    """"
//            +"        ${dbField.jooqFieldName} = createField(DSL.unquotedName("${dbField.columnName}"), DefaultDataType.getDataType(SQLDialect.DEFAULT, ${dbField.jooqFieldType}::class.java), this)"
//            +"    """ } }"
//            +"    }"
//+""
//            +"    companion object {"
//            +"        val TABLE = ${dbTable.jooqDslName}()"
//            +"    }"
//            +"}"
//        """
    }
}
