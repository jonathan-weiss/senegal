package ch.senegal.example.persistence.book

import org.jooq.*
import org.jooq.impl.*
import java.util.*


class BookDsl private constructor(
    alias: Name = DSL.unquotedName("BOOK"),
    aliased: Table<Record>? = null
) :
    TableImpl<Record>(alias, null, aliased, null) {
    val BOOK_ID: Field<UUID>
    val BOOK_NAME: Field<String>
    val MAIN_AUTHOR_ID: Field<UUID>
    override fun `as`(alias: String?): BookDsl {
        return BookDsl(DSL.quotedName(alias))
    }

    private constructor(alias: Name) : this(alias, TABLE)

    init {
        BOOK_ID = createField(DSL.unquotedName("ID"), DefaultDataType.getDataType(SQLDialect.DEFAULT, UUID::class.java), this)
        BOOK_NAME = createField(DSL.unquotedName("BOOK_NAME"), DefaultDataType.getDataType(SQLDialect.DEFAULT, String::class.java), this)
        MAIN_AUTHOR_ID = createField(DSL.unquotedName("MAIN_AUTHOR_ID"), DefaultDataType.getDataType(SQLDialect.DEFAULT, UUID::class.java), this)
    }

    companion object {
        val TABLE = BookDsl()
    }
}

