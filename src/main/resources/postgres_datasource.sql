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

-- Delete Schema 'public'
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE public.role (
	role_id SERIAL NOT NULL,
    role_name TEXT NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE public.user (
	user_id SERIAL NOT NULL,
    email TEXT NOT NULL,
    "password" TEXT NOT NULL,
	status INT NOT NULL,
    role_id SERIAL NOT NULL,
    PRIMARY KEY (user_id),
    FOREIGN KEY (role_id) REFERENCES public.role(role_id)
);

CREATE TABLE public.admin (
	admin_id SERIAL NOT NULL,
    full_name TEXT NOT NULL,
    email TEXT NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    user_id SERIAL NOT NULL,
    PRIMARY KEY (admin_id),
    FOREIGN KEY (user_id) REFERENCES public.user(user_id)
);

CREATE TABLE public.manager (
	manager_id SERIAL NOT NULL,
    full_name TEXT NOT NULL,
    address TEXT NOT NULL,
    email TEXT NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
   	user_id SERIAL NOT NULL,
    PRIMARY KEY (manager_id),
    FOREIGN KEY (user_id) REFERENCES public.user(user_id)
);

CREATE TABLE public.customer (
	customer_id SERIAL NOT NULL,
    full_name TEXT NOT NULL,
    address TEXT NOT NULL,
    email TEXT NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    user_id SERIAL NOT NULL,
    PRIMARY KEY (customer_id),
    FOREIGN KEY (user_id) REFERENCES public.user(user_id)
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
    "email" VARCHAR(50) NOT NULL,
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
	status INT NOT NULL,
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
	"status" INT NOT NULL, -- true: còn món, false: hết món
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
INSERT INTO public.role (role_name) VALUES
('ADMIN'),
('MANAGER'),
('CUSTOMER');

-- Thêm dữ liệu vào bảng "account"
INSERT INTO public.user (email, "password", status, role_id) VALUES
('admin1@example.com', '123', 1, 1),
('manager1@gmail.com', '123', 1, 2),
('manager2@gmail.com', '456', 1, 2),
('customer1@gmail.com', '001', 1, 3),
('customer2@gmail.com', '002', 1, 3),
('customer3@gmail.com', '003', 1, 3);

-- Thêm dữ liệu vào bảng "manager"
INSERT INTO public.admin (full_name, email, phone_number, user_id) VALUES
('Admin 1', 'admin1@example.com', '1234567890', 1);

INSERT INTO public.manager (full_name, address, email, phone_number, user_id) VALUES
('Phan Anh Khoa', 'Heaven', 'khoa@example.com', '0978263486', 2),
('Ngo Chi Cuong', 'Unspecified', 'cuong@example.com', '0862935985', 3);

-- Thêm dữ liệu vào bảng "customer"
INSERT INTO public.customer (full_name, address, email, phone_number, user_id) VALUES
('Alice Johnson', '123 Main St', 'alice@example.com', '5551234567', 1),
('Bob Wilson', '456 Elm St', 'bob@example.com', '5559876543', 2);

-- -- Thêm dữ liệu vào bảng "movie-genre"
-- INSERT INTO public."movie-genre" VALUES
--     ('genre001', 'Action', 'Exciting action-packed movies'),
--     ('genre002', 'Romance', 'Heartwarming romantic films');

-- -- Thêm dữ liệu vào bảng "studio"
-- INSERT INTO public."studio" VALUES
--     ('studio001', 'Studio A', '123 Studio St', 'studioA@example.com', '5551112222'),
--     ('studio002', 'Studio B', '456 Studio Rd', 'studioB@example.com', '5553334444');

-- -- Thêm dữ liệu vào bảng "language"
-- INSERT INTO public."language" VALUES
--     ('language001', 'English'),
--     ('language002', 'Spanish');

-- -- Thêm dữ liệu vào bảng "movie"
-- INSERT INTO public."movie" VALUES
--     ('movie001', 'Movie A', 'Director A', 2023, '2023-09-30', 'https://trailerA.com', 120, 18, 'Story A', 'studio001', 'language001'),
--     ('movie002', 'Movie B', 'Director B', 2023, '2023-09-30', 'https://trailerB.com', 110, 12, 'Story B', 'studio001', 'language001');

-- -- Thêm dữ liệu vào bảng "rating"
-- INSERT INTO public."rating" VALUES
--     ('rating001', 4.5, 'Great movie!', '2023-09-30', 'movie001', 'customer001'),
--     ('rating002', 3.0, 'Not bad', '2023-09-30', 'movie001', 'customer002');

-- -- Thêm dữ liệu vào bảng "detail-movie-genre"
-- INSERT INTO public."detail-movie-genre" VALUES
--     ('movie001', 'genre001'),
--     ('movie002', 'genre002');

-- -- Thêm dữ liệu vào bảng "format"
-- INSERT INTO public."format" VALUES
--     ('format001', '2D'),
--     ('format002', '3D');

-- -- Thêm dữ liệu vào bảng "theater"
-- INSERT INTO public."theater" VALUES
--     ('theater001', 'Theater A', '123 Theater St', '5555555555', 5),
--     ('theater002', 'Theater B', '456 Theater Rd', '5556666666', 3);

-- -- Thêm dữ liệu vào bảng "room"
-- INSERT INTO public."room" VALUES
--     ('room001', 'Room 1', 100, 'theater001'),
--     ('room002', 'Room 2', 80, 'theater002');

-- -- Thêm dữ liệu vào bảng "showtime"
-- INSERT INTO public."showtime" VALUES
--     ('showtime001', '14:00:00', '16:00:00', 100, 'movie001', 'room001', 'format001'),
--     ('showtime002', '17:00:00', '19:00:00', 120, 'movie002', 'room002', 'format002');

-- -- Thêm dữ liệu vào bảng "ticket-status"
-- INSERT INTO public."ticket-status" VALUES
--     ('status001', 'Available'),
--     ('status002', 'Booked'),
--     ('status003', 'Sold Out');

-- -- Thêm dữ liệu vào bảng "seat"
-- INSERT INTO public."seat" VALUES
--     ('seat001', 'Seat 1', 'Standard', 'room001'),
--     ('seat002', 'Seat 2', 'VIP', 'room001');

-- -- Thêm dữ liệu vào bảng "ticket"
-- INSERT INTO public."ticket" VALUES
--     ('ticket001', 'showtime001', 'seat001', 'status001'),
--     ('ticket002', 'showtime002', 'seat002', 'status001');

-- -- Thêm dữ liệu vào bảng "menu"
-- INSERT INTO public."menu" VALUES
--     ('item001', 'Item 1', 10, 'https://item1.jpg', TRUE),
--     ('item002', 'Item 2', 15, 'https://item2.jpg', FALSE);

SELECT * FROM public.role
SELECT * FROM public.user
SELECT * FROM public.admin
SELECT * FROM public.manager
SELECT * FROM public.customer

update public.user 
set "password" = '$2a$12$xkbSzvuOLK0Yil9HQFqe3OAj/34CSLK7vK2iz2jzM7XNC/H04Zs5.' 
where user_id = 1




