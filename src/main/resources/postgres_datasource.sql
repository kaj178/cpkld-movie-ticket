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
-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;

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

--Edit database ---

-- Thể loại phim (hành động, tình cảm,...)
CREATE TABLE public."movie-genre" (
	"genre_id" Serial NOT NULL,
	"genre_name" TEXT NOT NULL,
	"description" TEXT NOT NULL,
	PRIMARY KEY (genre_id)
);

CREATE TABLE public."studio" (
	"studio_id" Serial NOT NULL,
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

-- Edit db--
CREATE TABLE public."movie" (
	"movie_id" Serial NOT NULL,
	"movie_name" TEXT NOT NULL,
	"director" TEXT NOT NULL,
	"year" int NOT NULL,
	"premiere" DATE NOT NULL, -- ngày khởi chiếu
	"url_trailer" VARCHAR(500) NOT NULL,
    --Edit--
	"time" Time NOT NULL,
	"age" INT NOT NULL,
	"story" TEXT NOT NULL,
    --Chinh lai khoa ngoai--
	"studio_id" SERIAL NOT NULL,
	"language_id" VARCHAR(50) NOT NULL,
	PRIMARY KEY (movie_id),
	FOREIGN KEY (studio_id) REFERENCES public."studio"(studio_id),
	FOREIGN KEY (language_id) REFERENCES public."language"(language_id)
);

-- Edit ---


CREATE TABLE public."rating" (
	"rating_id" VARCHAR(50) NOT NULL,
	"score" FLOAT NOT NULL,
	"comment" TEXT NULL,
	"day" DATE NOT NULL,
	"movie_id" SERIAL NOT NULL,
	"customer_id" SERIAL NOT NULL,
	PRIMARY KEY (rating_id),
	FOREIGN KEY (movie_id) REFERENCES public."movie"(movie_id),
	FOREIGN KEY (customer_id) REFERENCES public."customer"(customer_id)
);

CREATE TABLE public."detail-movie-genre" (
	"movie_id" SERIAL NOT NULL,
	"genre_id" SERIAL NOT NULL,
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
	"movie_id" SERIAL NOT NULL,
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
	"status" INT NOT NULL, -- 1 = true: còn món, 0 = false: hết món
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
('manager2@gmail.com', '456', 1, 2);
update public.user
-- hashing 123 password
set "password" = '$2a$12$xkbSzvuOLK0Yil9HQFqe3OAj/34CSLK7vK2iz2jzM7XNC/H04Zs5.'
where user_id = 1;

-- Thêm dữ liệu vào bảng "manager"
INSERT INTO public.admin (full_name, email, phone_number, user_id) VALUES
('Admin 1', 'admin1@example.com', '1234567890', 1);

INSERT INTO public.manager (full_name, address, email, phone_number, user_id) VALUES
('Phan Anh Khoa', 'Heaven', 'khoa@example.com', '0978263486', 2),
('Ngo Chi Cuong', 'Unspecified', 'cuong@example.com', '0862935985', 3);

-- Thêm dữ liệu vào bảng "customer"
-- INSERT INTO public.customer (full_name, address, email, phone_number, user_id) VALUES

-- -- Thêm dữ liệu vào bảng "movie-genre"
INSERT INTO public."movie-genre" ("genre_name",	"description") VALUES
('Kinh dị', 'Phim mang đến trải nghiệm kinh dị, đáng sợ với các yếu tố ma quái và huyền bí.'),
('Hài', 'Phim mang đến tiếng cười và giải trí, tập trung vào yếu tố hài hước và sự vui nhộn.'),
('Tình cảm', 'Các câu chuyện xoay quanh các mối quan hệ tình cảm, nổi bật với tình yêu và cảm xúc.'),
('Tài liệu', 'Phim tài liệu thực tế, chân thực mô tả sự kiện, người nổi tiếng hoặc chủ đề cụ thể.'),
('Bí ẩn', 'Các câu chuyện với yếu tố bí ẩn, kèm theo sự hồi hộp và giải đố.'),
('Hành động', 'Phim với những cảnh hành động và phiêu lưu, thường đi kèm với chiến đấu và đấu tranh.'),
('Hoạt hình', 'Phim dùng kỹ thuật hoạt hình để tạo ra các nhân vật và cảnh quay.'),
('Phiêu lưu', 'Các cuộc phiêu lưu mạo hiểm, thường bao gồm khám phá và đối mặt với nguy hiểm.'),
('Hồi hộp', 'Phim tạo cảm giác hồi hộp và căng thẳng, thường với các yếu tố truyền hình.'),
('Tâm lý', 'Các câu chuyện tâm lý sâu sắc, tập trung vào tâm trạng và tâm lý nhân vật.'),
('Gia đình', 'Phim hướng đến gia đình và mối quan hệ gia đình, thường mang đến thông điệp tích cực.'),
('Giả tưởng', 'Các câu chuyện khoa học viễn tưởng và huyền bí, thường xoay quanh thế giới ảo.'),
('Thần thoại', 'Các câu chuyện về thần thoại và huyền bí, thường liên quan đến các vị thần và sinh vật siêu nhiên.'),
('Tội phạm', 'Phim tập trung vào các hoạt động tội phạm, bao gồm hành động truy nã và phá án.');

-- -- Thêm dữ liệu vào bảng "studio"
insert into public.studio ("studio_name", "address", "email", "phone_number") Values 
('Screen Gems', '', '', ''),
('Calendar Studios', '', '', ''),
('CoMix Wave Films', '', '', ''),
('My Way Pictures', '', '', ''),
('Live On', '', '', ''),
('Climax Studio', '', '', ''),
('DC Studios', '', '', ''),
('Amazon Studios', '', '', ''),
('Skybound', '', '', ''),
('CJ HK Entertainment', '', '', ''),
('Film Bridge International', '', '', ''),
('Galaxy Studio', '', '', ''),
('Marvel Studios', '', '', ''),
('Fowler Media', '', '', ''),
('DandeLion Animation Studio', '', '', ''),
('Universal Pictures', '', '', ''),
('MT Entertainment', '', '', ''),
('Entertainment One', '', '', ''),
('Universal Pictures', '', '', ''),
('Lucasfilm Ltd.', '', '', ''),
('Mainframe Studios', '', '', ''),
('DreamWorks Animation', '', '', ''),
('Skydance Media', '', '', ''),
('Walt Disney Pictures', '', '', ''),
('Columbia Pictures', '', '', ''),
('Lý Hải Production', '', '', ''),
('Galaxy Studio', '', '', ''),
('Studio Ghibli', '', '', ''),
('STXfilms', '', '', ''),
('World Pictures', '', '', '');

-- -- Thêm dữ liệu vào bảng "language"
INSERT INTO public."language" VALUES
('language001', 'Tiếng Anh'),
('language002', 'Tiếng Pháp'),
('language003', 'Tiếng Trung'),
('language004', 'Tiếng Nhật'),
('language005', 'Tiếng Hàn'),
('language006', 'Tiếng Việt'),
('language007', 'Tiếng Thái'),
('language008', 'Tiếng Malay'),
('language009', 'Tiếng Ý');

-- -- Thêm dữ liệu vào bảng "movie"
INSERT INTO public."movie" ("movie_name", "director", "year", "premiere", "url_trailer", "time", "age"," story", "studio_id", "language_id") VALUES
('Khắc tinh của quỷ', 'Julius Avery', 2023, '4/14/2023', 'https://www.youtube.com/watch?v=SXfnUAW9gwA', '01:43:00', 18, 'Theo chân Amorth trong cuộc điều tra về vụ quỷ ám kinh hoàng của một cậu bé và dần khám phá ra những bí mật hàng thế kỷ mà Vatican đã cố gắng giấu kín', 1, 'language001'),
('Chuyện tôi và ma quỷ thành người một nhà', 'Trình Vĩ Hào', 2022, '4/7/2023', 'https://www.youtube.com/watch?v=zxk_YEa2Ky0', '02:10:00', 18, 'Một cuộc hành trình giả tưởng đầy tiếng cười và nước mắt giữa một người đàn ông thẳng thắn và một con ma đồng tính', 2, 'language003'),
('Khóa chặt cửa nào Suzume', 'Makoto Shinkai', 2022, '3/10/2023', 'https://www.youtube.com/watch?v=xQ4_c8JfuzI', '02:02:00', 0, 'Để bảo vệ Nhật Bản khỏi thảm họa, những cánh cửa rải rác khắp nơi phải được đóng lại, và bất ngờ thay Suzume cũng có khả năng đóng cửa đặc biệt này', 3, 'language004'),
('Đảo tội ác', 'euho', 2023, '3/31/2023', 'https://www.youtube.com/watch?v=jSZUpx_3yL4', '01:54:00', 18, 'Nhóm du khách trẻ vô tình phá bỏ phong ấn của con quái vật khát máu khi đến tham quan một hòn đảo cấm không dân địa phương nào dám đặt chân đến', 4, 'language008'),
('Biệt đội rất ổn', 'Tạ Nguyên Hiệp', 2023, '3/31/2023', 'https://www.youtube.com/watch?v=XHvNz4g88pE', '01:44:00', 13, 'Màn kết hợp bất đắc dĩ của Biệt Đội Rất Ổn và Gia Đình Cục Súc tại khu resort sang chảnh hứa hẹn cực kỳ gay cấn, hồi hộp nhưng không kém phần hài hước, xúc động', 5, 'language006'),
('Tri kỷ', 'Young-Keun Min', 2023, '3/24/2023', 'https://www.youtube.com/watch?v=K46K_1yTwUg', '02:04:00', 16, 'Khi Ham Jin Woo bước vào giữa cả hai, đó cũng là lúc những vết nứt trong mối quan hệ của Mi So và Ha Eun xuất hiện', 6, 'language005'),
('Cơn thịnh nộ của các vị thần', 'David F. Sandberg', 2023, '3/17/2023', 'https://www.youtube.com/watch?v=l37LjoV9W7M', '02:10:00', 13, 'Một tác phẩm từ New Line Cinema mang tên “Shazam! Fury of the Gods,” tiếp tục câu chuyện về chàng trai Billy Batson, và bản ngã Siêu anh hùng trưởng thành của mình sau khi hô cụm từ “SHAZAM !”', 7, 'language001'),
('Air-Theo đuổi một huyền thoại', 'Ben Affleck', 2023, '4/14/2023', 'https://www.youtube.com/watch?v=0h9vZ52Vals', '01:52:00', 16, 'Từ đạo diễn đã từng đoạt giải thưởng Ben Affleck, AIR hé lộ mối quan hệ đột phá giữa huyền thoại Michael Jordan khi mới bắt đầu sự nghiệp và bộ phận bóng rổ còn non trẻ của Nike, đã làm thay đổi thế giới thể thao và văn hóa đương đại với thương hiệu Air Jordan', 8, 'language001'),
('Renfield tay sai của quỷ', 'Chris McKay', 2023, '4/14/2023', 'https://www.youtube.com/watch?v=vXkN2H3Ijyw', '01:32:00', 18, 'Renfield bị buộc phải bắt con mồi về cho chủ nhân và thực hiện mọi mệnh lệnh, kể cả việc đó nhục nhã như thế nào. Nhưng giờ đây, sau nhiều thế kỷ làm nô lệ, Renfield đã sẵn sàng để khám phá cuộc sống bên ngoài cái bóng của Hoàng Tử Bóng Đêm', 9, 'language001'),
('Tình chị duyên em', 'Weawwan Hongvivatana', 2023, '4/7/2023', 'https://www.youtube.com/watch?v=DGKsSNmVPKA', '02:01:00', 13, 'Hai chị em sinh đôi giống hệt nhau là “You” và “Me” cũng đang lo lắng cho tương lai của họ về việc “làm sao sống mà có thể thiếu vắng nhau”. Cặp song sinh thân thiết với nhau đến mức có thể chia sẻ mọi khía cạnh cuộc sống cho nhau, kể cả nụ hôn đầu', 10, 'language007'),
('Câu lạc bộ sát thủ', 'Camille Delamarre', 2023, '4/7/2023', 'https://www.youtube.com/watch?v=neDUFSt8N0Q', '01:51:00', 18, 'Morgan Gaines - một sát thủ chuyên nghiệp có nhiệm vụ phải giết bảy người, Morgan phát hiện ra bảy "mục tiêu" cũng là bảy sát thủ nặng ký. Lối thoát duy nhất cho Morgan là tìm ra kẻ chủ mưu đứng sau tất cả mọi chuyện', 11, 'language001'),
('Siêu lừa gặp siêu lầy', 'Võ Thanh Hòa', 2023, '3/3/2023', 'https://www.youtube.com/watch?v=NIVa1CCdFv4', '01:52:00', 16, 'Theo chân của Khoa – tên lừa đảo tầm cỡ “quốc nội” đến đảo ngọc Phú Quốc với mong muốn đổi đời. Tại đây, Khoa gặp Tú – tay lừa đảo “hàng real” và cùng Tú thực hiện các phi vụ từ nhỏ đến lớn', 12, 'language006'),
('Chàng trai xinh đẹp của tôi', 'Sakai Mai', 2023, '5/26/2023', 'https://www.youtube.com/watch?v=Wy0f83FHbYY', '01:43:00', 18, 'Phim kể về mối tình tuyệt đẹp thời học sinh của hai chàng trai Hira và Kiyoi So. Hira vốn là một chàng trai hướng nội lại có tật nói lắp nên đã bị bạn bè bắt nạt', 13, 'language004'),
('Mặt nạ quỷ', 'Lawrence Fowler', 2023, '4/14/2023', 'https://www.youtube.com/watch?v=3MKRzG9k76Q', '01:42:00', 18, 'Bí ẩn về cái chết của em gái Evie 20 năm trước còn bỏ ngỏ, vào lúc 09:09 hằng đêm, hàng loạt cuộc chạm trán kinh hoàng xảy ra. Liệu Margot có biết được sự thật ai là kẻ giết em gái mình?', 14, 'language001'),
('The first slam dunk', 'Takehiko Inoue, Yasuyuki Ebara', 2023, '4/14/2023', 'https://www.youtube.com/watch?v=NEa0J_Q-NIY', '02:04:00', 13, 'Phim theo chân Ryota Miyagi, hậu vệ của đội bóng rổ trường trung học Shohoku. Anh có một người anh trai, Sota, hơn anh ba tuổi và là người đã truyền cảm hứng cho tình yêu bóng rổ của anh', 15, 'language004'),
('Phim anh em Super Mario', 'Aaron Horvath, Michael Jelenic', 2023, '4/7/2023', 'https://www.youtube.com/watch?v=QcinmCfoh8E', '01:33:00', 0, 'Câu chuyện về cuộc phiêu lưu của anh em Super Mario đến vương quốc Nấm', 16, 'language001'),
('Người giữ thời gian', 'Mỹ Tâm, Tạ Nguyên Hiệp', 2023, '4/8/2023', 'https://www.youtube.com/watch?v=yiI_McOCaw4', '01:46:00', 0, 'Khi cánh cổng thời gian mở ra, Tú và các người bạn của mình bắt đầu hành trình vượt qua các thời kỳ khác nhau để tìm kiếm và giữ gìn bản sắc văn hóa Việt Nam', 17, 'language006'),
('Ngục tối & rồng: Danh dự của kẻ trộm', 'John Francis Daley, Jonathan Goldstein', 2023, '4/21/2023', 'https://www.youtube.com/watch?v=P4IA6pIVb-w', '02:14:00', 13, 'Theo chân một tên trộm quyến rũ và một nhóm những kẻ bịp bợm nghiệp dư thực hiện vụ trộm sử thi nhằm lấy lại một di vật đã mất, nhưng mọi thứ trở nên nguy hiểm khó lường hơn bao giờ hết khi họ đã chạm trán nhầm người', 18, 'language001'),
('Nhà vịt di cư', 'Benjamin Renner', 2023, '12/22/2023', 'https://www.youtube.com/watch?v=865RCGVYcSc', '00:00:00', 0, 'Nhà Vịt Di Cư theo chân một gia đình vịt trời gồm vịt bố, vịt mẹ, cậu con trai tuổi teen Dax và vịt út Gwen, trong lần đầu tiên trải nghiệm chuyến di cư tiến về phía nam để trú đông. Thế nhưng, niềm vui vẻ sự háo hức kéo dài không bao lâu, gia đình vịt nhận ra, họ đang bay ngược chiều với tất cả các đàn vịt khác', 19, 'language001'),
('Indiana Jones và vòng quay định mệnh', 'James Mangold', 2023, '6/29/2023', 'https://www.youtube.com/watch?v=6u93f9fQ8yY', '02:34:00', 16, 'Indiana Jones 5 sẽ pha trộn giữa thực tế, hư cấu khi lấy bối cảnh năm 1969, lần này Indiana Jones sẽ đối mặt với Đức quốc xã trong thời gian diễn ra cuộc chạy đua vào không gian', 20, 'language001'),
('Barbie: Công chúa phiêu lưu', 'Conrad Helten', 2020, '9/1/2020', 'https://www.youtube.com/watch?v=7joR5V_T3wQ', '01:12:00', 0, 'Barbie thực hiện một chuyến đi đến Vương quốc Floravia theo lời mời của công chúa Amelia của Floravia', 21, 'language001'),
('Blue Beetle', 'Angel Manuel Soto', 2023, '8/18/2023', 'https://www.youtube.com/watch?v=j5rpK0Ba7q8', '02:08:00', 13, 'Cậu sinh viên mới tốt nghiệp Jaime Reyes trở về nhà với tràn trề niềm tin và hy vọng về tương lai, để rồi nhận ra quê nhà của anh đã thay đổi rất nhiều so với trước đây', 7, 'language001'),
('Quỷ lùn tinh nghịch: Đồng tâm hiệp nhạc', 'Walt Dohrn, Tim Heitz', 2023, '11/17/2023', 'https://www.youtube.com/watch?v=izi44lM_HSo', '01:32:00', 0, 'Sự xuất hiện đột ngột của John Dory, anh trai thất lạc đã lâu của Branch mở ra quá khứ bí mật được che giấu bấy lâu nay của Branch. Đó là quá khứ về một ban nhạc có tên BroZone từng rất nổi tiếng nhưng đã tan rã', 22, 'language001'),
('Transformers: Quái thú trỗi dậy', 'Steven Caple Jr.', 2023, '6/9/2023', 'https://www.youtube.com/watch?v=lRBA1AKyUaI', '02:07:00', 13, 'Bộ phim dựa trên sự kiện Beast Wars trong loạt phim hoạt hình "Transformers", nơi mà các robot có khả năng biến thành động vật khổng lồ cùng chiến đấu chống lại một mối đe dọa tiềm tàng', 23, 'language001'),
('Flash', 'Andy Muschietti', 2023, '6/16/2023', 'https://www.youtube.com/watch?v=cvn0h6cuUPQ', '02:24:00', 16, 'Mùa hè này, đa thế giới sẽ va chạm khốc liệt với những bước chạy của FLASH', 'studio007', 'language001'),
('Xứ sở các nguyên tố', 'Peter Sohn', 2023, '6/23/2023', 'https://www.youtube.com/watch?v=1Vg1hL435OI', '01:49:00', 13, 'Xứ Sở Các Nguyên Tố từ Disney và Pixar lấy bối cảnh tại thành phố các nguyên tố, nơi lửa, nước, đất và không khí cùng chung sống với nhau. Câu chuyện xoay quanh nhân vật Ember, một cô nàng cá tính, thông minh, mạnh mẽ và đầy sức hút. Tuy nhiên, mối quan hệ của cô với Wade - một anh chàng hài hước, luôn thuận thế đẩy dòng - khiến Ember cảm thấy ngờ vực với thế giới này', 24, 'language001'),
('Vú em dạy yêu', 'Gene Stupnitsky', 2023, '6/23/2023', 'https://www.youtube.com/watch?v=q_XWWWlA39k', '01:43:00', 18, 'Không dành cho bé dưới 18!.. Red Band Trailer với Jennifer Lawrence trở lại, nóng bỏng cả mắt trong tựa phim hài-bựa-lầy nhất hè năm nay', 25, 'language001'),
('Lật mặt 6: Tấm vé định mệnh', 'Lý Hải', 2023, '4/28/2023', 'https://www.youtube.com/watch?v=2EnP2tVC00Q', '02:12:00', 16, 'Lật mặt 6 sẽ thuộc thể loại giật gân, tâm lý pha hành động, hài hước', 26, 'language006'),
('Con Nhót mót chồng', 'Vũ Ngọc Đãng', 2023, '4/28/2023', 'https://www.youtube.com/watch?v=e7KHOQ-alqY', '01:52:00', 16, 'Bộ phim là câu chuyện của Nhót - người phụ nữ “chưa kịp già” đã sắp bị mãn kinh, vội vàng đi tìm chồng. Nhưng sâu thẳm trong cô, là khao khát muốn có một đứa con và luôn muốn hàn gắn với người cha suốt ngày say xỉn của mình', 27, 'language006'),
('Mèo Siêu Quậy ở Viện Bảo Tàng', 'Vasiliy Rovenskiy', 2023, '4/28/2023', 'https://www.youtube.com/watch?v=xj4cfU9SHIM', '01:20:00', 0, 'Câu chuyện xoay quanh tình bạn của chú mèo Vincent và chú chuột Maurice. Vincent vừa nhận được công việc bảo vệ kiệt tác tranh Mona Lisa trong một viện bảo tàng, còn Maurice lại có niềm đam mê gặm nhấm bức tranh này. Mọi chuyện phức tạp hơn khi có người cũng đang nung nấu ý định cướp lấy tuyệt tác Mona Lisa', 28, 'language001'),
('Vệ binh dải Ngân Hà 3', 'James Gunn', 2023, '5/3/2023', 'https://www.youtube.com/watch?v=89aYxQcGGA4', '02:29:00', 13, 'Cho dù vũ trụ này có bao la đến đâu, các Vệ Binh của chúng ta cũng không thể trốn chạy mãi mãi', 13, 'language001'),
('Khế ước', 'Guy Ritchie', 2023, '4/21/2023', 'https://www.youtube.com/watch?v=npHvcDj45rg', '02:03:00', 18, 'Trong nhiệm vụ cuối cùng ở Afghanistan, Trung sĩ John Kinley cùng đội với phiên dịch viên bản địa Ahmed. Khi đơn vị của họ bị phục kích, Kinley và Ahmed là 2 người sống sót duy nhất. Bị kẻ địch truy đuổi, Ahmed liều mạng đưa Kinley đang bị thương băng qua nhiều dặm đường địa hình khắc nghiệt đến nơi an toàn. Trở về Mỹ, Kinley biết rằng Ahmed và gia đình không dc cấp giấy thông hành tới Mỹ như đã hứa', 29, 'language001'),
('Đầu gấu đụng đầu đất', 'Park Sung Kwang', 2023, '4/21/2023', 'https://www.youtube.com/watch?v=MagrY1rpOT4', '01:38:00', 16, 'Phim Đầu Gấu Đụng Đầu Đất dựa trên câu chuyện thần thoại nổi tiếng tại Hàn Quốc về hai chú gấu sinh đôi hoá thành người sau khi ăn tỏi và ngải cứu trong 100 ngày', 10, 'language005'),
('Âm vực chết', 'Alessandro Antonaci, Stefano Mandalà, Daniel Lascar', 2023, '4/21/2023', 'https://www.youtube.com/watch?v=CmBuZXqkyLM', '01:33:00', 18, 'Sau cái chết của cha, Emma (Penelope Sangiorgi) vội vã bay từ New York về quê nhà ở Ý để lo hậu sự. Trong thời gian diễn ra tang lễ, Emma ở một mình trong căn nhà mà cha mẹ để lại. Lúc này, cô bị buộc phải đối mặt với một thực thể tà ác có khả năng kết nối thông qua một chiếc radio bị nguyền rủa', 30, 'language009');


-- -- Thêm dữ liệu vào bảng "rating"
-- INSERT INTO public."rating" VALUES
--     ('rating001', 4.5, 'Great movie!', '2023-09-30', 'movie001', 'customer001'),
--     ('rating002', 3.0, 'Not bad', '2023-09-30', 'movie001', 'customer002');

-- -- Thêm dữ liệu vào bảng "detail-movie-genre"
INSERT INTO public."detail-movie-genre" VALUES
(1, 1),
(2, 2),
(2, 5),
(2, 6),
(3, 7),
(3, 8),
(4, 1),
(5, 2),
(6, 9),
(6, 10),
(7, 6),
(7, 8),
(8, 10),
(9, 1),
(9, 2),
(10, 2),
(10, 3),
(11, 6),
(12, 2),
(12, 6),
(13, 3),
(14, 1),
(15, 2),
(15, 7),
(16, 2),
(16, 7),
(16, 8),
(17, 4),
(18, 6),
(18, 8),
(18, 13),
(19, 7),
(20, 6),
(20, 8),
(21, 7),
(22, 6),
(22, 8),
(22, 12),
(23, 2),
(23, 7),
(23, 8),
(24, 6),
(24, 8),
(24, 12),
(25, 6),
(25, 8),
(25, 13),
(26, 2),
(26, 7),
(26, 11),
(27, 2),
(28, 2),
(28, 6),
(28, 9),
(28, 10),
(29, 2),
(29, 10),
(30, 2),
(30, 7),
(30, 8),
(31, 6),
(31, 8),
(31, 13),
(32, 6),
(32, 9),
(32, 14),
(33, 2),
(33, 6),
(34, 1);

-- -- Thêm dữ liệu vào bảng "format"
INSERT INTO public."format" VALUES
('format001', '2D'),
('format002', '3D');

INSERT INTO public."theater" VALUES
  ('theater001', 'Nam Sài Gòn', 'Lotte Mart, Tầng 3 Lotte Mart NSG, 469 Đ. Nguyễn Hữu Thọ, Tân Hưng, Quận 7, TP.HCM', '2837752521', 6),
  ('theater002', 'Gò Vấp', 'Tầng 3, Lotte Mart Gò Vấp, 242 Nguyễn Văn Lượng, Q.Gò Vấp, TP.HCM', '2836202151', 7),
  ('theater003', 'Cộng Hòa', 'Tầng 4, Pico Plaza, 20 Cộng Hòa, P.12, Q.Tân Bình, TP.HCM', '2837752526', 6),
  ('theater004', 'Thủ Đức', 'Tầng 2 Joy Citipoint, KCX Linh Trung, KP4, P Linh Trung, Q.Thủ Đức, TP.HCM', '2837245751', 5),
  ('theater005', 'Moonlight', 'Tầng 2 Moonlight Residences, Số 102 Đặng Văn Bi, P. Bình Thọ, Q. Thủ Đức, TP.HCM', '2836206474', 6),
  ('theater006', 'Cantavil', 'Tầng 7, Cantavil Premier, Số 1 đường Song Hành, Xa lộ Hà Nội, P.An Phú, Q.2, TP.HCM', '2837402323', 8),
  ('theater007', 'Nowzone', 'Tầng 5, TTTM Nowzone, 235 Nguyễn Văn Cừ, P.Nguyễn Cư Trinh, Q.1, TP.HCM', '2839262255', 4);

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




