package ch.cassiamon.engine.domain.templating

interface TargetFileCollectionProvider {
        fun getTargetFiles(): List<TargetFileWithContent>
}
