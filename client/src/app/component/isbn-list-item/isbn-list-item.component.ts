import { Isbn } from './../../model/isbn';
import { Component, OnInit, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-isbn-list-item',
  templateUrl: './isbn-list-item.component.html',
  styleUrls: ['./isbn-list-item.component.css']
})
export class IsbnListItemComponent implements OnInit {

  @Input() isbn:Isbn;

  validCharacters = /^[0-9- ]+$/;

  constructor() { }

  ngOnInit(): void {
  }

  onPaste(event: ClipboardEvent) {
    this.validateIsbnNumber(event.clipboardData.getData("text"));
  }

  onChange() {
    this.validateIsbnNumber(this.isbn.bookNumber);
  }

  keyPress(event: KeyboardEvent) {
    if(!this.validCharacter(String.fromCharCode(event.charCode))) {
      event.preventDefault();
    }
  }

  keyUp() {
    this.isbn.valid = null;
  }

  private validateIsbnNumber(isbnNumber:string) {
    if(isbnNumber !== "" && !this.validCharacters.test(isbnNumber)) {
      this.isbn.invalidReason = "Invalid Input";
    } else {
      this.isbn.invalidReason = "";
    }
  }

  private validCharacter(character:string) {
    if(character.match(this.validCharacters)) {
      return true;
    } else {
      return false;
    }
  }

}
