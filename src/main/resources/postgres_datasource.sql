CREATE DATABASE "movie-ticket"
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
	"role_id" VARCHAR(50) NOT NULL,
    "role_name" TEXT NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE public."account" (
	"account_id" VARCHAR(50) NOT NULL,
    "email" VARCHAR(20) NOT NULL,
    "password" VARCHAR(50) NOT NULL,
    "role_id" VARCHAR(50) NOT NULL,
    PRIMARY KEY (account_id),
    FOREIGN KEY (role_id) REFERENCES public."role"(role_id)
);

CREATE TABLE public."manager" (
	"manager_id" VARCHAR(50) NOT NULL,
    "full_name" TEXT NOT NULL,
    "email" VARCHAR(20) NOT NULL,
    "phone_number" VARCHAR(10) NOT NULL,
    "account_id" VARCHAR(50) NOT NULL,
    PRIMARY KEY (manager_id),
    FOREIGN KEY (account_id) REFERENCES public."account"(account_id)
);

CREATE TABLE public."customer" (
	"customer_id" VARCHAR(50) NOT NULL,
    "full_name" TEXT NOT NULL,
    "address" TEXT NOT NULL,
    "email" VARCHAR(20) NOT NULL,
    "phone_number" VARCHAR(10) NOT NULL,
    "account_id" VARCHAR(50) NOT NULL,
    PRIMARY KEY (customer_id),
    FOREIGN KEY (account_id) REFERENCES public."account"(account_id)
);

-- Thể loại phim (hành động, tình cảm,...)
CREATE TABLE public."movie-genre" (
	"genre_id" VARCHAR(50) NOT NULL,
    "genre_name" TEXT NOT NULL,
    "description" TEXT NOT NULL,
    PRIMARY KEY (genre_id)
);

CREATE TABLE public."studio" (
	"studio_id" VARCHAR(50) NOT NULL,
    "studio_name" TEXT NOT NULL,
    "address" TEXT NOT NULL,
    "email" VARCHAR(20) NOT NULL,
    "phone_number" VARCHAR(10) NOT NULL,
    PRIMARY KEY (studio_id)
);

CREATE TABLE public."language" (
	"language_id" VARCHAR(50) NOT NULL,
	"language_name" TEXT NOT NULL,
    PRIMARY KEY (language_id)
);

CREATE TABLE public."movie" (
	"movie_id" VARCHAR(50) NOT NULL,
    "movie_name" TEXT NOT NULL,
    "director" TEXT NOT NULL,
    "year" int NOT NULL,
    "premiere" DATE NOT NULL, -- ngày khởi chiếu
    "url_trailer" VARCHAR(500) NOT NULL,
    "time" INT NOT NULL,
    "age" INT NOT NULL,
    "story" TEXT NOT NULL,
    "studio_id" VARCHAR(50) NOT NULL,
    "language_id" VARCHAR(50) NOT NULL,
    PRIMARY KEY (movie_id),
    FOREIGN KEY (studio_id) REFERENCES public."studio"(studio_id),
    FOREIGN KEY (language_id) REFERENCES public."language"(language_id)
);

CREATE TABLE public."rating" (
	"rating_id" VARCHAR(50) NOT NULL,
    "score" FLOAT NOT NULL,
    "comment" TEXT NULL,
    "day" DATE NOT NULL,
    "movie_id" VARCHAR(50) NOT NULL,
    "customer_id" VARCHAR(50) NOT NULL,
    PRIMARY KEY (rating_id),
    FOREIGN KEY (movie_id) REFERENCES public."movie"(movie_id),
    FOREIGN KEY (customer_id) REFERENCES public."customer"(customer_id)
);

CREATE TABLE public."detail-movie-genre" (
	"movie_id" VARCHAR(50) NOT NULL,
	"genre_id" VARCHAR(50) NOT NULL,
	PRIMARY KEY (movie_id, genre_id),
	FOREIGN KEY (movie_id) REFERENCES public."movie"(movie_id),
	FOREIGN KEY (genre_id) REFERENCES public."movie-genre"(genre_id)
);

-- Loại rạp (2D, 3D, ...)
CREATE TABLE public."format" (
	"format_id" VARCHAR(50) NOT NULL,
	"format_name" TEXT NOT NULL,
	PRIMARY KEY (format_id)
);

CREATE TABLE public."theater" (
	"theater_id" VARCHAR(50) NOT NULL,
	"theater_name" TEXT NOT NULL,
	"address" TEXT NOT NULL,
	"phone" VARCHAR(10) NOT NULL,
	"number_of_room" INT NOT NULL,
	PRIMARY KEY (theater_id)
);

CREATE TABLE public."room" (
	"room_id" VARCHAR(50) NOT NULL,
	"room_name" VARCHAR(50) NOT NULL,
	"number_of_seats" INT NOT NULL,
	"theater_id" VARCHAR(50) NOT NULL,
	PRIMARY KEY (room_id),
	FOREIGN KEY (theater_id) REFERENCES public."theater"(theater_id)
);

CREATE TABLE public."showtime" (
	"showtime_id" VARCHAR(50) NOT NULL,
	"start_time" TIME NOT NULL,
	"end_time" TIME NOT NULL,
	"price" INT NOT NULL,
	"movie_id" VARCHAR(50) NOT NULL,
	"room_id" VARCHAR(50) NOT NULL,
	"format_id" VARCHAR(50) NOT NULL,
	PRIMARY KEY (showtime_id),
	FOREIGN KEY (movie_id) REFERENCES public."movie"(movie_id),
	FOREIGN KEY (room_id) REFERENCES public."room"(room_id),
	FOREIGN KEY (format_id) REFERENCES public."format"(format_id)
);

-- Trống hoặc đã được đặt
CREATE TABLE public."ticket-status" (
	"status_id" VARCHAR(50) NOT NULL,
	"status_name" TEXT NOT NULL,
	PRIMARY KEY (status_id)
);

CREATE TABLE public."seat" (
	"seat_id" VARCHAR(50) NOT NULL,
	"seat_name" TEXT NOT NULL,
	"type" TEXT DEFAULT N'Thường' NOT NULL, -- Ghế VIP hoặc thường
	"room_id" VARCHAR(50) NOT NULL,
	PRIMARY KEY (seat_id),
	FOREIGN KEY (room_id) REFERENCES public."room"(room_id)
);

CREATE TABLE public."ticket" (
	"ticket_id" VARCHAR(50) NOT NULL,
	"showtime_id" VARCHAR(50) NOT NULL,
	"seat_id" VARCHAR(50) NOT NULL,
	"status_id" VARCHAR(50) NOT NULL,
	PRIMARY KEY (ticket_id),
	FOREIGN KEY (showtime_id) REFERENCES public."showtime"(showtime_id),
	FOREIGN KEY (seat_id) REFERENCES public."seat"(seat_id),
	FOREIGN KEY (status_id) REFERENCES public."ticket-status"(status_id)
);

CREATE TABLE public."menu" (
	"item_id" VARCHAR(50) NOT NULL,
	"name" TEXT NOT NULL,
	"price" INT NOT NULL,
	"image_url" TEXT NOT NULL,
	"status" BOOLEAN NOT NULL, -- true: còn món, false: hết món
	PRIMARY KEY (item_id)
);

CREATE TABLE public."promotion" (
	"promotion_id" VARCHAR(50) NOT NULL,
	"promotion_name" TEXT NOT NULL,
	"description" TEXT NOT NULL,
	"start_time" TIMESTAMP NOT NULL,
	"end_time" TIMESTAMP NOT NULL,
	"discount" INT NOT NULL, 
	"code" TEXT NOT NULL,
	"url_image" TEXT NOT NULL,
	PRIMARY KEY (promotion_id)
);

CREATE TABLE public."booking" (
	"booking_id" VARCHAR(50) NOT NULL,
	"number_of_tickets" INT NOT NULL,
	"total_price" INT NOT NULL,
	"booking_time" TIMESTAMP NOT NULL,
	"show_time" TIMESTAMP NOT NULL,
	"status" BOOLEAN NOT NULL,
	"promotion_id" VARCHAR(50) NOT NULL,
	PRIMARY KEY (booking_id),
	FOREIGN KEY (promotion_id) REFERENCES public."promotion"(promotion_id)
);


-- Thêm dữ liệu vào bảng "role"
INSERT INTO public."role" ("role_id", "role_name") VALUES
    ('ADMIN', 'Admin'),
    ('MANAGER', 'Manager'),
    ('CUSTOMER', 'Customer');

-- Thêm dữ liệu vào bảng "account"
INSERT INTO public."account" ("account_id", "email", "password", "role_id") VALUES
    ('admin001', 'admin@example.com', 'hashed_password', 'ADMIN'),
    ('manager001', 'manager@example.com', 'hashed_password', 'MANAGER'),
    ('customer001', 'customer@example.com', 'hashed_password', 'CUSTOMER');

-- Thêm dữ liệu vào bảng "manager"
INSERT INTO public."manager" ("manager_id", "full_name", "email", "phone_number", "account_id") VALUES
    ('manager001', 'John Smith', 'john@example.com', '1234567890', 'manager001'),
    ('manager002', 'Jane Doe', 'jane@example.com', '9876543210', 'manager001');

-- Thêm dữ liệu vào bảng "customer"
INSERT INTO public."customer" ("customer_id", "full_name", "address", "email", "phone_number", "account_id") VALUES
    ('customer001', 'Alice Johnson', '123 Main St', 'alice@example.com', '5551234567', 'customer001'),
    ('customer002', 'Bob Wilson', '456 Elm St', 'bob@example.com', '5559876543', 'customer001');
	
INSERT INTO public.customer VALUES
	('customer003', 'Name 3', 'Address 3', 'name3@gmail.com', 'phonenum3', 'customer001'),
	('customer004', 'Name 4', 'Address 4', 'name4@gmail.com', 'phonenum4', 'customer001'),
	('customer005', 'Name 5', 'Address 5', 'name5@gmail.com', 'phonenum5', 'customer001'),
	('customer006', 'Name 6', 'Address 6', 'name6@gmail.com', 'phonenum6', 'customer001'),
	('customer007', 'Name 7', 'Address 7', 'name7@gmail.com', 'phonenum7', 'customer001'),
	('customer008', 'Name 8', 'Address 8', 'name8@gmail.com', 'phonenum8', 'customer001'),
	('customer009', 'Name 9', 'Address 9', 'name9@gmail.com', 'phonenum9', 'customer001');

-- Thêm dữ liệu vào bảng "movie-genre"
INSERT INTO public."movie-genre" ("genre_id", "genre_name", "description") VALUES
    ('genre001', 'Action', 'Exciting action-packed movies'),
    ('genre002', 'Romance', 'Heartwarming romantic films');

-- Thêm dữ liệu vào bảng "studio"
INSERT INTO public."studio" ("studio_id", "studio_name", "address", "email", "phone_number") VALUES
    ('studio001', 'Studio A', '123 Studio St', 'studioA@example.com', '5551112222'),
    ('studio002', 'Studio B', '456 Studio Rd', 'studioB@example.com', '5553334444');

-- Thêm dữ liệu vào bảng "language"
INSERT INTO public."language" ("language_id", "language_name") VALUES
    ('language001', 'English'),
    ('language002', 'Spanish');

-- Thêm dữ liệu vào bảng "movie"
INSERT INTO public."movie" ("movie_id", "movie_name", "director", "year", "premiere", "url_trailer", "time", "age", "story", "studio_id", "language_id") VALUES
    ('movie001', 'Movie A', 'Director A', 2023, '2023-09-30', 'https://trailerA.com', 120, 18, 'Story A', 'studio001', 'language001'),
    ('movie002', 'Movie B', 'Director B', 2023, '2023-09-30', 'https://trailerB.com', 110, 12, 'Story B', 'studio001', 'language001');

-- Thêm dữ liệu vào bảng "rating"
INSERT INTO public."rating" ("rating_id", "score", "comment", "day", "movie_id", "customer_id") VALUES
    ('rating001', 4.5, 'Great movie!', '2023-09-30', 'movie001', 'customer001'),
    ('rating002', 3.0, 'Not bad', '2023-09-30', 'movie001', 'customer002');

-- Thêm dữ liệu vào bảng "detail-movie-genre"
INSERT INTO public."detail-movie-genre" ("movie_id", "genre_id") VALUES
    ('movie001', 'genre001'),
    ('movie002', 'genre002');

-- Thêm dữ liệu vào bảng "format"
INSERT INTO public."format" ("format_id", "format_name") VALUES
    ('format001', '2D'),
    ('format002', '3D');

-- Thêm dữ liệu vào bảng "theater"
INSERT INTO public."theater" ("theater_id", "theater_name", "address", "phone", "number_of_room") VALUES
    ('theater001', 'Theater A', '123 Theater St', '5555555555', 5),
    ('theater002', 'Theater B', '456 Theater Rd', '5556666666', 3);

-- Thêm dữ liệu vào bảng "room"
INSERT INTO public."room" ("room_id", "room_name", "number_of_seats", "theater_id") VALUES
    ('room001', 'Room 1', 100, 'theater001'),
    ('room002', 'Room 2', 80, 'theater002');

-- Thêm dữ liệu vào bảng "showtime"
INSERT INTO public."showtime" ("showtime_id", "start_time", "end_time", "price", "movie_id", "room_id", "format_id") VALUES
    ('showtime001', '14:00:00', '16:00:00', 100, 'movie001', 'room001', 'format001'),
    ('showtime002', '17:00:00', '19:00:00', 120, 'movie002', 'room002', 'format002');

-- Thêm dữ liệu vào bảng "ticket-status"
INSERT INTO public."ticket-status" ("status_id", "status_name") VALUES
    ('status001', 'Available'),
    ('status002', 'Booked'),
    ('status003', 'Sold Out');

-- Thêm dữ liệu vào bảng "seat"
INSERT INTO public."seat" ("seat_id", "seat_name", "type", "room_id") VALUES
    ('seat001', 'Seat 1', 'Standard', 'room001'),
    ('seat002', 'Seat 2', 'VIP', 'room001');

-- Thêm dữ liệu vào bảng "ticket"
INSERT INTO public."ticket" ("ticket_id", "showtime_id", "seat_id", "status_id") VALUES
    ('ticket001', 'showtime001', 'seat001', 'status001'),
    ('ticket002', 'showtime002', 'seat002', 'status001');

-- Thêm dữ liệu vào bảng "menu"
INSERT INTO public."menu" ("item_id", "name", "price", "image_url", "status") VALUES
    ('item001', 'Item 1', 10, 'https://item1.jpg', TRUE),
    ('item002', 'Item 2', 15, 'https://item2.jpg', FALSE);

SELECT * FROM public.role
SELECT * FROM public."account"
SELECT * FROM public."customer"
SELECT * FROM public."detail-movie-genre"
SELECT * FROM public."format"
SELECT * FROM public."language"




