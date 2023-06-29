package ch.cassiamon.engine.inputsource

import ch.cassiamon.api.registration.ModelInputData

interface InputSourceDataProvider {
    fun provideModelInputData(): ModelInputData
}
