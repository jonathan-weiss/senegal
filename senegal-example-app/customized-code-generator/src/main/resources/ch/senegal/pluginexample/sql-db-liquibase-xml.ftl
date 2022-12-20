<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.4.xsd">

    <changeSet id="${templateModel.SqlDbTableName}_structure" author="Senegal">
        <createTable tableName="${templateModel.SqlDbTableName}">
            <!-- COLUMN: ${templateModel.SqlDbPrimaryKeyColumnName} (primary key) -->
            <column name="${templateModel.SqlDbPrimaryKeyColumnName}" type="${templateModel.SqlDbPrimaryKeyColumnType}">
                <constraints primaryKey="true"/>
            </column>
            <#list templateModel.childNodes as fieldNode>
            <!-- COLUMN: ${fieldNode.SqlDbColumnName} -->
            <column name="${fieldNode.SqlDbColumnName}" type="${fieldNode.SqlDbColumnType}">
                <constraints nullable="false"/>
            </column>
            </#list>
        </createTable>
    </changeSet>
</databaseChangeLog>
