package ch.senegal.engine.freemarker

object FreemarkerUtil {

    private const val templateResourceDirectory = "ftl_templates/"

    fun removeFreemarkerFileExtension(templateName: String): String {
        return templateName.removeSuffix(".ftl")
    }

    fun processFileAccessAndExcecutionRights(fileDescriptors: List<FreemarkerFileDescriptor>) {
        fileDescriptors.forEach {fileDescriptor -> processFileAccessAndExcecutionRights(fileDescriptor) }
    }

    fun processFileAccessAndExcecutionRights(fileDescriptor: FreemarkerFileDescriptor) {
        val targetFile = fileDescriptor.targetFile.toFile()
        targetFile.setReadable(true, false)
        if(targetFile.name.endsWith(".sh") || targetFile.name.endsWith(".bat")) {
            targetFile.setExecutable(true, false)
        }

    }

    fun processFileDescriptors(fileDescriptors: List<FreemarkerFileDescriptor>) {
        fileDescriptors
            .map { file -> file.targetFile.toFile().parentFile }
            .toSet()
            .forEach { dir -> dir.mkdirs()}
        val templateProcessor = FreemarkerTemplateProcessor(templateResourceDirectory)
        templateProcessor.processFileContentWithFreemarker(fileDescriptors)
        processFileAccessAndExcecutionRights(fileDescriptors)

    }

}

