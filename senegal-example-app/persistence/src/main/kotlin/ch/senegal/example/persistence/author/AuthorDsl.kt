package ch.senegal.example.persistence.author

import org.jooq.*
import org.jooq.impl.*
import java.util.*


class AuthorDsl private constructor(
    alias: Name = DSL.unquotedName("AUTHOR"),
    aliased: Table<Record>? = null
) :
    TableImpl<Record>(alias, null, aliased, null) {
    val AUTHOR_ID: Field<UUID>
    val FIRSTNAME: Field<String>
    val LASTNAME: Field<String>
    override fun `as`(alias: String?): AuthorDsl {
        return AuthorDsl(DSL.quotedName(alias))
    }

    private constructor(alias: Name) : this(alias, TABLE)

    init {
        AUTHOR_ID = createField(DSL.unquotedName("AUTHOR_ID"), DefaultDataType.getDataType(SQLDialect.DEFAULT, UUID::class.java), this)
        FIRSTNAME = createField(DSL.unquotedName("FIRSTNAME"), DefaultDataType.getDataType(SQLDialect.DEFAULT, String::class.java), this)
        LASTNAME = createField(DSL.unquotedName("LASTNAME"), DefaultDataType.getDataType(SQLDialect.DEFAULT, String::class.java), this)
    }

    companion object {
        val TABLE = AuthorDsl()
    }
}

