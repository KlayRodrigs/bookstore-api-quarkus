CREATE TABLE IF NOT EXISTS books
(
    id             SERIAL PRIMARY KEY,
    isbn           VARCHAR(17) UNIQUE NOT NULL,
    title          VARCHAR(150)       NOT NULL,
    description    TEXT,
    category       VARCHAR(100)       NOT NULL,
    author         VARCHAR(100)       NOT NULL,
    publisher      VARCHAR(150)       NOT NULL,
    published_date DATE
);

CREATE TABLE IF NOT EXISTS users
(
    id           SERIAL PRIMARY KEY,
    cpf          CHAR(11) UNIQUE     NOT NULL,
    email        VARCHAR(255) UNIQUE NOT NULL,
    name         VARCHAR(100)        NOT NULL,
    phone        VARCHAR(20)         NOT NULL,
    street       VARCHAR(100)        NOT NULL,
    house_number VARCHAR(20)         NOT NULL,
    neighborhood VARCHAR(100)        NOT NULL,
    postal_code  CHAR(8)             NOT NULL,
    city         VARCHAR(100)        NOT NULL,
    state        CHAR(2)             NOT NULL
);


CREATE TABLE IF NOT EXISTS borrows
(
    id          SERIAL PRIMARY KEY,
    user_id     INT  NOT NULL,
    book_id     INT  NOT NULL,
    date        DATE NOT NULL,
    due_date    DATE NOT NULL,
    return_date DATE,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS reservation
(
    id      SERIAL PRIMARY KEY,
    user_id INT  NOT NULL,
    book_id INT  NOT NULL,
    date    DATE NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE RESTRICT
);
