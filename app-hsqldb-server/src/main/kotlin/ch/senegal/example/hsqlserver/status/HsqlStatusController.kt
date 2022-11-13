package ch.senegal.example.hsqlserver.status

import ch.senegal.example.hsqlserver.server.HsqlServerWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HsqlStatusController() {

    @Autowired
    private lateinit var hsqlServerWrapper: HsqlServerWrapper

    @GetMapping
    fun hsqlStatus(): String {
        val status = hsqlServerWrapper.getStatus()
        return "Status of HSQL: $status"
    }
}
