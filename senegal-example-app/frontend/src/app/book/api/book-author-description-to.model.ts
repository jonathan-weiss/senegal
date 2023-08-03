import {AuthorIdTO} from "../../author/api/author-id-to.model";


export interface BookAuthorDescriptionTO {
    authorId: AuthorIdTO,
    firstname: string,
    lastname: string,
}
