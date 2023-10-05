CREATE DATABASE `movie-ticket`;
USE `movie-ticket`;

CREATE TABLE `role` (
	role_id VARCHAR(10) NOT NULL,
    role_name NVARCHAR(20) NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE `account` (
	account_id VARCHAR(10) NOT NULL,
    email VARCHAR(20) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    role_id VARCHAR(10) NOT NULL,
    PRIMARY KEY (account_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE `manager` (
	manager_id VARCHAR(10) NOT NULL,
    full_name NVARCHAR(50) NOT NULL,
    email VARCHAR(20) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    account_id VARCHAR(10) NOT NULL,
    PRIMARY KEY (manager_id),
    FOREIGN KEY (account_id) REFERENCES `account`(account_id)
);

CREATE TABLE `customer` (
	customer_id VARCHAR(10) NOT NULL,
    full_name NVARCHAR(50) NOT NULL,
    address NVARCHAR(50) NOT NULL,
    email VARCHAR(20) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    account_id VARCHAR(10) NOT NULL,
    PRIMARY KEY (customer_id),
    FOREIGN KEY (account_id) REFERENCES `account`(account_id)
);

-- Thể loại
CREATE TABLE `movie-genre` (
	genre_id VARCHAR(10) NOT NULL,
    genre_name NVARCHAR(20) NOT NULL,
    `description` NVARCHAR(100) NOT NULL,
    PRIMARY KEY (genre_id)
);

CREATE TABLE `rating` (
	rating_id VARCHAR(10) NOT NULL,
    score DOUBLE NOT NULL,
    comment NVARCHAR(200) NULL,
    day DATETIME NOT NULL,
    movie_id VARCHAR(10) NOT NULL,
    customer_id VARCHAR(10) NOT NULL,
    PRIMARY KEY (rating_id),
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

create table `studio` (
	studio_id varchar(10) not null,
    studio_name nvarchar(20) not null,
    address nvarchar(50) not null,
    email varchar(20) not null,
    phone_number varchar(10) not null,
    primary key (studio_id)
);

create table `language` (
	language_id varchar(10) not null,
	language_name nvarchar(20) not null,
    primary key (language_id)
);

create table `movie` (
	movie_id varchar(10) not null,
    movie_name nvarchar(50) not null,
    director nvarchar(50) not null,
    `year` int not null,
    premiere datetime not null, -- ngày khởi chiếu
    url_trailer varchar(500) not null,
    `time` int not null,
    age int not null,
    story nvarchar(1000) not null,
    studio_id varchar(10) not null,
    language_id varchar(10) not null,
    primary key (movie_id),
    foreign key (studio_id) references studio(studio_id),
    foreign key (language_id) references `language`(language_id)
);

