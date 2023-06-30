package ch.cassiamon.api

class DomainUnitName private constructor(name: String): NamedId(name) {

    companion object {
        fun of(name: String): DomainUnitName {
            return DomainUnitName(name)
        }
    }
}
