package ch.senegal.engine.logger

import ch.senegal.engine.virtualfilesystem.VirtualFileSystem
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

class SenegalLogger(virtualFileSystem: VirtualFileSystem) {
    private val logger: Logger = Logger.getLogger("senegal")

    init {
        // must set before the Logger
        // loads logging.properties from the classpath
        virtualFileSystem.classpathResourceAsInputStream("/senegal-logging.properties").use {
            LogManager.getLogManager().readConfiguration(it)
        }
    }
    fun logDebug(msg: String) {
        logger.log(Level.FINE, msg)
    }

    fun logDebug(msgProvider: () -> String) {
        logger.log(Level.FINE, msgProvider)
    }

    fun logUserInfo(msg: String) {
        logger.log(Level.INFO, msg)
    }

    fun logUserInfo(msgProvider: () -> String) {
        logger.log(Level.INFO, msgProvider)
    }

    fun logWarnings(msg: String) {
        logger.log(Level.WARNING, msg)
    }

    fun logWarnings(msgProvider: () -> String) {
        logger.log(Level.WARNING, msgProvider)
    }

}
