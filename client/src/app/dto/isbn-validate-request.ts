import { IsbnDto } from './isbn-dto';
export class IsbnValidateRequest {
    constructor(public isbnList?:Array<IsbnDto>) {
        
    }
}