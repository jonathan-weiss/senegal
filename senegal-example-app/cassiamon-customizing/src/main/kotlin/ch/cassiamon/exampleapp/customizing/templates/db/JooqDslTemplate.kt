package ch.cassiamon.exampleapp.customizing.templates.db

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.forEach

object JooqDslTemplate {

    fun fillTemplate(dbTable: DbTable): String {
        return """
            package ${dbTable.jooqDslPackage}
            
            import org.jooq.*
            import org.jooq.impl.*
            import java.util.*
            
            class ${dbTable.jooqDslName} private constructor(
                alias: Name = DSL.unquotedName("${dbTable.tableName}"),
                aliased: Table<Record>? = null
            ) : TableImpl<Record>(alias, null, aliased, null) {
            
                val ${dbTable.primaryKeyJooqFieldName}: Field<${dbTable.primaryKeyJooqFieldType}>
                ${forEach(dbTable.tableFields()) { dbField ->
            """
                val ${dbField.jooqFieldName}: Field<${dbField.jooqFieldType}>
            """ } }

                override fun `as`(alias: String): ${dbTable.jooqDslName} {
                    return ${dbTable.jooqDslName}(DSL.quotedName(alias))
                }

                private constructor(alias: Name) : this(alias, TABLE)

                init {
                    ${dbTable.primaryKeyJooqFieldName} = createField(DSL.unquotedName("${dbTable.primaryKeyColumnName}"), DefaultDataType.getDataType(SQLDialect.DEFAULT, ${dbTable.primaryKeyJooqFieldType}::class.java), this)
                    ${forEach(dbTable.tableFields()) { dbField ->
                """
                    ${dbField.jooqFieldName} = createField(DSL.unquotedName("${dbField.columnName}"), DefaultDataType.getDataType(SQLDialect.DEFAULT, ${dbField.jooqFieldType}::class.java), this)
                """ } }
                }

                companion object {
                    val TABLE = ${dbTable.jooqDslName}()
                }
            }
        """.identForMarker()
    }
}
