import {BookIdTO} from "./book-id-to.model";
import {AuthorIdTO} from "../../author/api/author-id-to.model";


export interface UpdateBookInstructionTO {
    bookId: BookIdTO;
    bookName: string;
    mainAuthorId: AuthorIdTO;
}

