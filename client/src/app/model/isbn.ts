export class Isbn {
    bookNumber:string;
    valid:boolean;
    invalidReason:string;

    constructor() {
        this.bookNumber = "";
        this.valid = null;
        this.invalidReason="";
    }
}