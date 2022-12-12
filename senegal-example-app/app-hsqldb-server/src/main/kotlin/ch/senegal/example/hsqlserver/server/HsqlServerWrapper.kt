package ch.senegal.example.hsqlserver.server

class HsqlServerWrapper(
    private val databaseName: String,
    private val databasePath: String,
    private val user: String,
    private val password: String,
    private val port: Int
) {
    private val dbIndex = 0
    private val hsqlDbServer = org.hsqldb.server.Server()

    fun getStatus(): String {
        return """
            HSQL-Version: ${hsqlDbServer.productVersion}, 
            ServerId: ${hsqlDbServer.serverId}, 
            Port: ${hsqlDbServer.port}, 
            Protocol: ${hsqlDbServer.protocol}, 
            DatabaseName: ${hsqlDbServer.getDatabaseName(dbIndex, false)}, 
            DatabaseName (config): ${hsqlDbServer.getDatabaseName(dbIndex, true)}, 
            DatabasePath (config): ${hsqlDbServer.getDatabasePath(dbIndex, false)}, 
            DatabasePath: ${hsqlDbServer.getDatabasePath(dbIndex, true)}
        """.trimIndent()
    }

    private fun configureServer() {
        hsqlDbServer.port = port
        // see https://stackoverflow.com/questions/8817780/hsqldb-server-mode-username-password
        hsqlDbServer.setDatabasePath(
            dbIndex,
            "file:$databasePath/$databaseName;user=$user;password=$password;sql.syntax_ora=true"
        )
        hsqlDbServer.setDatabaseName(dbIndex, databaseName)
        hsqlDbServer.isNoSystemExit = true
        hsqlDbServer.isSilent = false
    }

    fun startServer() {
        configureServer()
        println("Start hsqldb server ${hsqlDbServer.getDatabaseType(dbIndex)}.")
        hsqlDbServer.start()
    }

    fun stopServer() {
        println("Stop hsqldb server.")
        hsqlDbServer.stop()
    }
}
