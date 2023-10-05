CREATE DATABASE movie-ticket
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
SET search_path TO 'movie-ticket'

CREATE TABLE public."role" (
	"role_id" VARCHAR(10) NOT NULL,
    "role_name" TEXT NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE public."account" (
	"account_id" VARCHAR(10) NOT NULL,
    "email" VARCHAR(20) NOT NULL,
    "password" VARCHAR(50) NOT NULL,
    "role_id" VARCHAR(10) NOT NULL,
    PRIMARY KEY (account_id),
    FOREIGN KEY (role_id) REFERENCES public."role"(role_id)
);

CREATE TABLE public."manager" (
	"manager_id" VARCHAR(10) NOT NULL,
    "full_name" TEXT NOT NULL,
    "email" VARCHAR(20) NOT NULL,
    "phone_number" VARCHAR(10) NOT NULL,
    "account_id" VARCHAR(10) NOT NULL,
    PRIMARY KEY (manager_id),
    FOREIGN KEY (account_id) REFERENCES public."account"(account_id)
);

CREATE TABLE public."customer" (
	"customer_id" VARCHAR(10) NOT NULL,
    "full_name" TEXT NOT NULL,
    "address" TEXT NOT NULL,
    "email" VARCHAR(20) NOT NULL,
    "phone_number" VARCHAR(10) NOT NULL,
    "account_id" VARCHAR(10) NOT NULL,
    PRIMARY KEY (customer_id),
    FOREIGN KEY (account_id) REFERENCES public."account"(account_id)
);

-- Thể loại
CREATE TABLE public."movie-genre" (
	"genre_id" VARCHAR(10) NOT NULL,
    "genre_name" TEXT NOT NULL,
    "description" TEXT NOT NULL,
    PRIMARY KEY (genre_id)
);

CREATE TABLE public."studio" (
	"studio_id" VARCHAR(10) NOT NULL,
    "studio_name" TEXT NOT NULL,
    "address" TEXT NOT NULL,
    "email" VARCHAR(20) NOT NULL,
    "phone_number" VARCHAR(10) NOT NULL,
    PRIMARY KEY (studio_id)
);

CREATE TABLE public."language" (
	"language_id" VARCHAR(10) NOT NULL,
	"language_name" TEXT NOT NULL,
    PRIMARY KEY (language_id)
);

CREATE TABLE public."movie" (
	"movie_id" VARCHAR(10) NOT NULL,
    "movie_name" TEXT NOT NULL,
    "director" TEXT NOT NULL,
    "year" int NOT NULL,
    "premiere" DATE NOT NULL, -- ngày khởi chiếu
    "url_trailer" VARCHAR(500) NOT NULL,
    "time" INT NOT NULL,
    "age" INT NOT NULL,
    "story" TEXT NOT NULL,
    "studio_id" VARCHAR(10) NOT NULL,
    "language_id" VARCHAR(10) NOT NULL,
    PRIMARY KEY (movie_id),
    FOREIGN KEY (studio_id) REFERENCES public."studio"(studio_id),
    FOREIGN KEY (language_id) REFERENCES public."language"(language_id)
);

CREATE TABLE public."rating" (
	"rating_id" VARCHAR(10) NOT NULL,
    "score" FLOAT NOT NULL,
    "comment" TEXT NULL,
    "day" DATE NOT NULL,
    "movie_id" VARCHAR(10) NOT NULL,
    "customer_id" VARCHAR(10) NOT NULL,
    PRIMARY KEY (rating_id),
    FOREIGN KEY (movie_id) REFERENCES public."movie"(movie_id),
    FOREIGN KEY (customer_id) REFERENCES public."customer"(customer_id)
);

CREATE TABLE public."detail-movie-genre" (
	"movie_id" VARCHAR(10) NOT NULL,
	"genre_id" VARCHAR(10) NOT NULL,
	PRIMARY KEY (movie_id, genre_id),
	FOREIGN KEY (movie_id) REFERENCES public."movie"(movie_id),
	FOREIGN KEY (genre_id) REFERENCES public."movie-genre"(genre_id)
);

CREATE TABLE public."format" (
	"format_id" VARCHAR(10) NOT NULL,
	"format_name" TEXT NOT NULL,
	PRIMARY KEY (format_id)
);

CREATE TABLE public."theater" (
	"theater_id" VARCHAR(10) NOT NULL,
	"theater_name" TEXT NOT NULL,
	"address" TEXT NOT NULL,
	"phone" VARCHAR(10) NOT NULL,
	"number_of_room" INT NOT NULL,
	PRIMARY KEY (theater_id)
);

CREATE TABLE public."room" (
	"room_id" VARCHAR(10) NOT NULL,
	"room_name" VARCHAR(50) NOT NULL,
	"number_of_seats" INT NOT NULL,
	"theater_id" VARCHAR(10) NOT NULL,
	PRIMARY KEY (room_id)
	FOREIGN KEY 
);





