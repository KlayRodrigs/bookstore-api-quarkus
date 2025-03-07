# 📚 Bookstore API

Welcome to the **Bookstore API**, a simple and efficient API for managing your book collection. This API allows you to easily add and retrieve books from your collection.

## ✨ Features
- **Get Books**: Retrieve a list of all books in the collection.
- **Add Book**: Add a new book to your collection.

## 🚀 Installation

To get started with the Bookstore API, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/KlayRodrigs/bookstore-api-quarkus.git
   cd bookstore-api
   ./mvnw install
   ```
2. Configure the application.properties
```
quarkus.http.port=${PORT:8081}
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=YOUR_USERNAME
quarkus.datasource.password=YOUR_PASSWORD

quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/YOUR_DATABASE_NAME
quarkus.hibernate-orm.database.generation=update
quarkus.datasource.jdbc.extended-leak-report = true

```
3. Create the database and table using postgresql. Remember that:
Queries need to have the same name than table. Example:

```
SELECT isbn, title, author, published_date, publisher, description, quantity FROM TABLE_NAME;
```

3. Run project
## 📖 Usage

Get All Books
To retrieve all books, send a GET request to:

```
GET /books
```

Add a New Book
To add a new book, send a POST request with the book details:
```
{
  "isbn": "978-0-7432-7359-9",
  "title": "Pride and Prejudice",
  "author": "Jane Austen",
  "published_date": "1813-01-28",
  "publisher": "T. Egerton",
  "description": "One of the most beloved novels in English literature, focusing on the story of Elizabeth Bennet and her relationship with the proud Mr. Darcy.",
  "quantity": 100
}

```
🛠️ Technologies Used
```
Quarkus: The Supersonic Subatomic Java Framework.
Hibernate ORM: For database interactions.
PostgreSQL: As the database for storing book information.
```

Happy coding! 🎉