import { IsbnValidateService } from './../../service/isbn-validate-service';
import { IsbnValidateResponse } from './../../dto/isbn-validate-response';
import { IsbnDto } from './../../dto/isbn-dto';
import { Isbn } from './../../model/isbn';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-isbn-list',
  templateUrl: './isbn-list.component.html',
  styleUrls: ['./isbn-list.component.css']
})
export class IsbnListComponent implements OnInit {

  isbnList:Array<Isbn> = [
    new Isbn()
  ]

  constructor(private isbnValidateService:IsbnValidateService) { }

  ngOnInit(): void {
  }

  addIsbn() {
    this.isbnList.push(new Isbn());
  }

  clearList() {
    this.isbnList = [
      new Isbn()
    ]
  }

  validate() {
        //Add only Isbns with non empty book numbers to be sent to web service
        let isbnDtoList = new Array<IsbnDto>();
        this.isbnList.forEach(isbn => {
          if(isbn.bookNumber !== null && isbn.bookNumber !== "") {
            isbnDtoList.push(new IsbnDto(isbn.bookNumber));
          }
        });
        
        //Send valid list to web service
        if(isbnDtoList != null && isbnDtoList.length > 0) {
          this.isbnValidateService.validateIsbns(isbnDtoList).subscribe((response:IsbnValidateResponse) => {
            console.log("response: " + JSON.stringify(response));
            for(let i = 0; i < this.isbnList.length; i++) {
              for(let x = 0; x < response.validatedIsbnList.length; x++) {
                if(this.isbnList[i].bookNumber === response.validatedIsbnList[x].isbn) {
                  this.isbnList[i].valid = response.validatedIsbnList[x].valid;
                  this.isbnList[i].invalidReason = response.validatedIsbnList[x].invalidReason;
                }
              }          
            }
          });
        }
  }

}
