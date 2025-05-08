
import { UuidTO } from '../../uuid-to.model';
import {AuthorIdTO} from "./author-id-to.model";

export interface AuthorTO {
    authorId: AuthorIdTO,
    firstname: string,
    nickname: string | null,
    lastname: string,
}
