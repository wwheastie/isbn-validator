# Isbn-Validator
Coding exercise to validate ISBNs. This project uses Angular, Java, Spring Boot, Maven, JUnit, and RESTful Services

# Connection Information
Client (Angular) Default Port: 4200
Server (Java) Default Port: 8080

## Web Service Details
### Validate ISBN-10
URL: /validate/ten/{isbn}
Method: GET  
Request Body: None  
Request Parameters: None  
Path Variable: isbn  
Return: ValidatedIsbn (Object)
Description: Validates an ISBN-10 and returns validation details

### Validate ISBN-13
URL: /validate/thirteen/{isbn}
Method: GET  
Request Body: None  
Request Parameters: None  
Path Variable: isbn  
Return: ValidatedIsbn (Object)
Description: Validates an ISBN-13 and returns validation details

### Validate All ISBNs
URL: /validate/all
Method: POST  
Request Body: IsbnDto (isbn[String])
Request Parameters: None
Path Variable: None
Return: IsbnValidatorAllResponse (Object)
Description: Validates all ISBN types and returns validation response
