package com.cpkld.repository;

import com.cpkld.model.entity.ShowTime;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer> {

        Optional<ShowTime> getShowTimeById(Integer showtimeId);

        @Query(value = "select * from public.showtime " +
                        "where start_time > :start " +
                        "and end_time < :end ", nativeQuery = true)
        Optional<List<ShowTime>> getAllShowTimeByDate(@Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        @Query(value = "select s.* from showtime s " +
                        "join movie m on s.movie_id = m.movie_id " +
                        "join detail_movie_genre dmg on m.movie_id = dmg.movie_id " +
                        "where dmg.genre_id = :genreId and start_time >= :startTime and end_time <= :endTime ", nativeQuery = true)
        Optional<List<ShowTime>> getShowTimeByDateAndGenre(
                        @Param("startTime") LocalDateTime start,
                        @Param("endTime") LocalDateTime end,
                        @Param("genreId") Integer genreId);

        @Query(value = "select s.* from showtime s " +
                        "join movie m on m.movie_id = s.movie_id " +
                        "join room r on r.room_id = s.room_id " +
                        "where r.theater_id = :theaterId and s.movie_id = :movieId ", nativeQuery = true)
        Optional<List<ShowTime>> getShowTimeByMovieAndTheater(
                        @Param("theaterId") Integer theaterId,
                        @Param("movieId") Integer movieId);

        @Query(value = "select s.* from showtime s " +
                        "join room r on r.room_id = s.room_id " +
                        "where r.theater_id = :theaterId and s.start_time > :startTime and s.end_time < :endTime ", nativeQuery = true)
        Optional<List<ShowTime>> getShowTimeByDateAndTheater(
                        @Param("startTime") LocalDateTime start,
                        @Param("endTime") LocalDateTime end,
                        @Param("theaterId") Integer theaterId);

        @Modifying
        @Transactional
        @Query(value = "INSERT INTO public.showtime (end_time, start_time, format_id, movie_id, room_id, price) "
                        +
                        "VALUES (:end_time, :start_time, :format_id, :movie_id, :room_id, :price);", nativeQuery = true)
        public void saveShowtime(
                        @Param("movie_id") Integer movie_id,
                        @Param("price") double price,
                        @Param("end_time") LocalDateTime end_time,
                        @Param("format_id") Integer format_id,
                        @Param("start_time") LocalDateTime start_time,
                        @Param("room_id") Integer room_id);
}
