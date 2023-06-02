package ch.cassiamon.api.template

data class TemplateRenderer<T>(
    val targetFilesWithModel: Set<TargetGeneratedFileWithModel<T>>,

    /*
     * Implemented as function, as the rendering of
     * the template may be omitted in certain cases.
     *
     * Implemented as an Iterator to support streaming
     * of large files without having them necessarily in
     * the memory as whole file.
     *
     * Implemented as a ByteIterator (instead of an e.g.
     * java.io.Reader) to also support the rare case
     * where binary files or file not in UTF encoding
     * should be written.
     */
    val templateRendererFunction: (targetGeneratedFileWithModel: TargetGeneratedFileWithModel<T>) -> ByteIterator,
) {
    fun renderAll(): Set<TemplateRenderingResult> {
        return targetFilesWithModel
            .map { TemplateRenderingResult(it.targetFile, templateRendererFunction(it)) }
            .toSet()
    }

}
