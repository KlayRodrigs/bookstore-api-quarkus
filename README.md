# 📚 Bookstore API

Welcome to the **Bookstore API**, a simple and efficient API for managing your book collection. This API allows you to easily add and retrieve books from your collection.

## ✨ Features
- **CRUD Book**
- **CRUD User**


## 🚀 Installation

To get started with the Bookstore API, follow these steps:

#### 1. Clone the repository:
   ```bash
   git clone https://github.com/KlayRodrigs/bookstore-api-quarkus.git
   cd bookstore-api
   ./mvnw install
   ```
#### 2. Configure the application.properties
```
quarkus.http.port=${PORT:8081}
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=YOUR_USERNAME
quarkus.datasource.password=YOUR_PASSWORD

quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/YOUR_DATABASE_NAME
quarkus.hibernate-orm.database.generation=update
quarkus.datasource.jdbc.extended-leak-report = true

```
#### 3. Create the database and table using postgresql. Remember that:
Queries need to have the same name than table. Example:

```
SELECT isbn, title, author, published_date, publisher, description, quantity FROM TABLE_NAME;
```

#### 4. Run project

## 🛠️ Testing the API
We recommend using tools like [Insomnia](https://insomnia.rest/) or [Postman](https://www.postman.com/) to test the API requests easily.

## 📖 Usage

Getters 
To retrieve all data, send a GET request to:

```
GET /book
GET /book/{id}
GET /user
GET /user/{id}
```

Add a New Book/Books
To add a new book/books, send a POST request with the details:
```
POST /book
[
  {
    "isbn": "978-0-7432-7359-9",
    "title": "Pride and Prejudice",
    "author": "Jane Austen",
    "published_date": "1813-01-28",
    "publisher": "T. Egerton",
    "description": "One of the most beloved novels in English literature, focusing on the story of Elizabeth Bennet and her relationship with the proud Mr. Darcy.",
    "quantity": 10
  }
]
```
Add a New User/Users
To add a new User/Users, send a POST request with the details:
```
POST /user
[
  {
    "cpf": "12345678901",
    "email": "joao.silva@email.com",
    "name": "João Silva",
    "phone": "11987654321",
    "street": "Rua das Flores",
    "house_number": "123",
    "neighborhood": "Centro",
    "postal_code": "01001000",
    "city": "São Paulo",
    "state": "SP"
  }
]
```
Edit the Book:
```
PUT /book/{id}
{
  "isbn": "978-0-7432-7359-9",
  "title": "Pride and Prejudice",
  "author": "Jane Austen",
  "published_date": "1813-01-28",
  "publisher": "T. Egerton",
  "description": "One of the most beloved novels in English literature, focusing on the story of Elizabeth Bennet and her relationship with the proud Mr. Darcy.",
  "quantity": 100 <- FIELD THAT YOU WANT TO CHANGE 
}
```
Edit the User:
```
PUT /user/{id}
{
  "cpf": "12345678901",
  "email": "joao.silva@email.com",
  "name": "João Silva",
  "phone": "11987654321",
  "street": "Rua das Flores",
  "house_number": "123",
  "neighborhood": "South", <- FIELD THAT YOU WANT TO CHANGE 
  "postal_code": "01001000",
  "city": "São Paulo",
  "state": "SP"
}
```
Deletes endpoint:
```
DELETE /book/{id}
DELETE /user/{id}
```

🛠️ Technologies Used
```
Quarkus: The Supersonic Subatomic Java Framework.
Hibernate ORM: For database interactions.
PostgreSQL: As the database for storing book information.
```

Happy coding! 🎉