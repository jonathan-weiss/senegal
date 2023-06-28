package ch.cassiamon.engine.domain.process

interface TargetFileCollectionProvider {
        fun getTargetFiles(): List<TargetFileWithContent>
}
