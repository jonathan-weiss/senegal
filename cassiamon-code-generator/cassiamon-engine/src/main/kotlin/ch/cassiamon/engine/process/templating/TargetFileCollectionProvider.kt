package ch.cassiamon.engine.process.templating

interface TargetFileCollectionProvider {
        fun getTargetFiles(): List<TargetFileWithContent>
}
