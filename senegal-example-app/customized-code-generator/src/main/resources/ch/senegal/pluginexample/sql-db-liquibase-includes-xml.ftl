<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.4.xsd">

<#list templateModel.childNodes as fieldNode>
    <include file="${fieldNode.SqlDbTableName}.structure.xml" relativeToChangelogFile="true"/>
    </#list>

</databaseChangeLog>
