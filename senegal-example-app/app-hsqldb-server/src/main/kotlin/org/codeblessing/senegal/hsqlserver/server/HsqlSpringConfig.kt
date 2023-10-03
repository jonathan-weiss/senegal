package org.codeblessing.senegal.hsqlserver.server

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

const val HSQL_STANDALONE_PORT_PROPERTY = "hsql.standalone.port"
const val HSQL_STANDALONE_USER_PROPERTY = "hsql.standalone.user"
const val HSQL_STANDALONE_PASSWORD_PROPERTY = "hsql.standalone.password"
const val HSQL_STANDALONE_DATABASE_NAME_PROPERTY = "hsql.standalone.database_name"
const val HSQL_STANDALONE_DATABASE_PATH_PROPERTY = "hsql.standalone.database_path"

@Configuration
class HsqlSpringConfig(
    @Value("\${$HSQL_STANDALONE_PORT_PROPERTY:0}") private val port: Int,
    @Value("\${$HSQL_STANDALONE_USER_PROPERTY:}") private val user: String,
    @Value("\${$HSQL_STANDALONE_PASSWORD_PROPERTY:}") private val password: String,
    @Value("\${$HSQL_STANDALONE_DATABASE_NAME_PROPERTY:}") private val dbName: String,
    @Value("\${$HSQL_STANDALONE_DATABASE_PATH_PROPERTY:}") private val dbPath: String,
) {

    @Bean(initMethod = "startServer", destroyMethod = "stopServer")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun getHsqlServer(): HsqlServerWrapper {
        return HsqlServerWrapper(dbName, dbPath, user, password, port)
    }
}
