plugins {
    kotlin("jvm") version "1.6.21" apply(false)
}

buildscript {
    apply(from = "config.gradle.kts")
}

val fileProjectVersion = readVersionFile()

allprojects {
    group = "ch.senegal"
    version = fileProjectVersion
}

fun readVersionFile(): String {
    // read first line to get rid of line breaks
    return rootDir.resolve("VERSION").readLines()[0].trim()
}

subprojects {
    /*
     * Composite tasks: clear, generate, produce, verify, audit, make
     *
     * These composite tasks are empty and defined on each subproject.
     * The names are by purpose chosen other than the task names known
     * by the java/kotlin plugin (assemble, check, test, build) as these
     * task should also be used in the angular/frontend part and to
     * avoid name conflicts.
     *
     * Only use these tasks to attach other task (by using dependsOn).
     */

    // CLEAR
    tasks.register("clear") {
        group = "Composite-Tasks"
        description = "clear all generated artefacts (generated code, classes, etc.)"
        dependsOn("clearFrontend")
        dependsOn("clearBackend")
    }

    tasks.register("clearBackend") {
        group = "Composite-Tasks"
        description = "clear tasks, but only for backend"
    }

    tasks.register("clearFrontend") {
        group = "Composite-Tasks"
        description = "clear tasks, but only for frontend"
    }

    // GENERATE
    tasks.register("generate") {
        group = "Composite-Tasks"
        description = "all code generation (e.g. openApi, MapStruct, Documentation generation, JOOQ, etc.)"
        dependsOn("generateFrontend")
        dependsOn("generateBackend")
    }

    tasks.register("generateBackend") {
        group = "Composite-Tasks"
        description = "generate tasks, but only for backend"
    }

    tasks.register("generateFrontend") {
        group = "Composite-Tasks"
        description = "generate tasks, but only for frontend"
    }

    // PRODUCE
    tasks.register("produce") {
        group = "Composite-Tasks"
        description = "tasks to compile and assemble"
        dependsOn("produceBackend")
        dependsOn("produceFrontend")
    }

    tasks.register("produceBackend") {
        group = "Composite-Tasks"
        description = "produce tasks, but only for backend"
        dependsOn("generateBackend")
    }

    tasks.register("produceFrontend") {
        group = "Composite-Tasks"
        description = "produce tasks, but only for frontend"
        dependsOn("generateFrontend")
    }

    // VERIFY
    tasks.register("verify") {
        group = "Composite-Tasks"
        description = "tasks to run unit tests like junit, karma/jasmin, etc."
        dependsOn("verifyBackend")
        dependsOn("verifyFrontend")
    }

    tasks.register("verifyBackend") {
        group = "Composite-Tasks"
        description = "verify task, but only for backend"
        dependsOn("produceBackend")
    }

    tasks.register("verifyFrontend") {
        group = "Composite-Tasks"
        description = "verify task, but only for frontend"
        dependsOn("produceFrontend")
    }

    // AUDIT
    tasks.register("audit") {
        group = "Composite-Tasks"
        description = "all lint tasks and static code analyses"
        dependsOn("auditBackend")
        dependsOn("auditFrontend")
    }

    tasks.register("auditBackend") {
        group = "Composite-Tasks"
        description = "audit task, but only for backend"
        dependsOn("produceBackend")
    }

    tasks.register("auditFrontend") {
        group = "Composite-Tasks"
        description = "audit task, but only for frontend"
        dependsOn("produceFrontend")
    }

    // MAKE
    tasks.register("make") {
        group = "Composite-Tasks"
        description = "All tasks from generate, produce, audit, verify"
        dependsOn("makeBackend")
        dependsOn("makeFrontend")
    }

    tasks.register("makeBackend") {
        group = "Composite-Tasks"
        description = "make task, but only for backend"
        dependsOn("produceBackend")
        dependsOn("verifyBackend")
        dependsOn("auditBackend")
    }

    tasks.register("makeFrontend") {
        group = "Composite-Tasks"
        description = "make task, but only for frontend"
        dependsOn("produceFrontend")
        dependsOn("verifyFrontend")
        dependsOn("auditFrontend")
    }
}
