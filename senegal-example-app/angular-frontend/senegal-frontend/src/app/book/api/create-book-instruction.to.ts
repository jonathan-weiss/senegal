import {AuthorIdTO} from "../../author/api/author-id-to.model";

export interface CreateBookInstructionTO {
    bookName: string;
    mainAuthorId: AuthorIdTO;
}

