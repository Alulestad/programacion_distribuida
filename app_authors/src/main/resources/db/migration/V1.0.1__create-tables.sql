-- Crear la tabla 'authors' si no existe
CREATE TABLE IF NOT EXISTS author (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL
);

-- Crear la tabla 'books' si no existe
CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    book_isbn VARCHAR(64) NOT NULL,
    book_title VARCHAR(64) NOT NULL,
    book_price DECIMAL(5, 2) NOT NULL,
    book_id_author INTEGER NOT NULL,
    FOREIGN KEY (book_id_author) REFERENCES author(id)
);