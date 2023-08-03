
import {AuthorIdTO} from "./author-id-to.model";


export interface UpdateAuthorInstructionTO {
    authorId: AuthorIdTO,
    firstname: string,
    lastname: string,
}
