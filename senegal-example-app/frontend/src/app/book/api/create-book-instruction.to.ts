import {UuidTO} from "../../uuid-to.model";

export interface CreateBookInstructionTO {
    bookName: string;
    mainAuthorId: UuidTO;
}

