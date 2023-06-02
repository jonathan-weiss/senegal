package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.tools.StringIdentHelper
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object LiquibaseTemplate {

    private const val ident = "  "

    private fun <T> forEach(iterator: Iterable<T>, out: (v:T)->String): String {
        val sb = StringBuilder()
        iterator.forEach {
            sb.append(out(it))
        }
        return sb.toString()
    }

    fun createLiquibaseXmlIndexFileTemplate(dbTables: List<DbTable>): String {
        return StringIdentHelper.identForMarker("""
            <?xml version="1.0" encoding="UTF-8"?>
            <databaseChangeLog
                    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.4.xsd">
            
                {nestedIdent}${forEach(dbTables) { "<include file=\"${it.liquibaseFileName}\" relativeToChangelogFile=\"true\"/>\n" } }{nestedIdent}
            
            </databaseChangeLog>
        """)
    }

    fun createLiquibaseXmlFileTemplate(dbTable: DbTable): String {
        return StringIdentHelper.identForMarker("""
            <?xml version="1.0" encoding="UTF-8"?>
            <databaseChangeLog
                    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.4.xsd">
            
                <changeSet id="${dbTable.tableName}_structure" author="Senegal">
                    <createTable tableName="${dbTable.tableName}">
                        <!-- COLUMN: ${dbTable.primaryKeyColumnName} (primary key) -->
                        <column name="${dbTable.primaryKeyColumnName}" type="${dbTable.primaryKeyColumnType}">
                            <constraints primaryKey="true"/>
                        </column>
                        ${forEach(dbTable.tableFields()) { 
                            """
                        <!-- COLUMN: ${it.columnName} -->
                        <column name="${it.columnName}" type="${it.columnType}">
                            <constraints nullable="false"/>
                        </column>
                            """ 
                        } }
                    </createTable>
                </changeSet>
            </databaseChangeLog>
        """)
    }
}
