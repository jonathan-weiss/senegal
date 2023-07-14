import { UuidTO } from '../../uuid-to.model';


export interface UpdateBookInstructionTO {
    bookId: UuidTO;
    bookName: string;
    mainAuthorId: UuidTO;
}

