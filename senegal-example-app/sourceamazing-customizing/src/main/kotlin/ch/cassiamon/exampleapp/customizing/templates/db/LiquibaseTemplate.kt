package ch.cassiamon.exampleapp.customizing.templates.db

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.forEach
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.ifElse
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.onlyIfIsInstance

object LiquibaseTemplate {

    fun createLiquibaseXmlIndexFileTemplate(dbTables: List<DbTable>): String {
        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <databaseChangeLog
                    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.4.xsd">
            
                {nestedIdent}${forEach(dbTables) { "<include file=\"${it.liquibaseFileName}\" relativeToChangelogFile=\"true\"/>\n" } }{nestedIdent}
            
            </databaseChangeLog>
        """.identForMarker()
    }

    fun createLiquibaseXmlFileTemplate(dbTable: DbTable): String {
        return """
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
                        ${forEach(dbTable.tableFields()) { dbField ->
            """
                        <!-- COLUMN: ${dbField.columnName} -->
                        <column name="${dbField.columnName}" type="${dbField.columnType}">
                            <constraints nullable="${ifElse(dbField.isMandatoryField, "false", "true")}" ${onlyIfIsInstance<ForeignKeyDbField>(dbField) { referenceDbField -> "foreignKeyName=\"${referenceDbField.referenceName}\" references=\"${referenceDbField.referencedDbTable.tableName}(${referenceDbField.referencedDbTable.primaryKeyColumnName})\"" }} />
                        </column>
                            """ 
                        } }
                    </createTable>
                </changeSet>
            </databaseChangeLog>
        """.identForMarker()
    }
}
