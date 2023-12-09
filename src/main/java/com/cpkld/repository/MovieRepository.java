package com.cpkld.repository;

import com.cpkld.model.entity.Movie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(value = "SELECT mv.*, lg.language_name " +
            "FROM public.movie as mv " +
            "JOIN public.language as lg " +
            "ON mv.language_id = lg.language_id", nativeQuery = true)
    Page<Movie> findAllMovies(Pageable pageable);

    @Query(value = "SELECT mv.* FROM public.movie mv " +
            "JOIN public.\"detail_movie_genre\" dmg ON mv.movie_id = dmg.movie_id " +
            "JOIN public.\"movie_genre\" mg ON mg.genre_id = dmg.genre_id " +
            "WHERE mg.genre_id = ?1 AND mv.year = 2023", nativeQuery = true)
    List<Movie> findPremiereMoviesByGenreId(Integer id);

    @Query(value = "select m.* from public.movie m " +
            "join public.showtime s on s.movie_id = m.movie_id " +
            "join public.ticket t on t.showtime_id = s.showtime_id " +
            "where t.ticket_id = :ticketId", nativeQuery = true)
    Movie findMovieByTicketId(@Param("ticketId") Integer ticketId);

    // List<Movie> findMoviesByMovieGenres(Integer movieGenreId);
    @Query(value = "SELECT movie_id FROM public.movie ORDER BY movie_id DESC LIMIT 1", nativeQuery = true)
    int getLastId();

    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO public.movie (movie_name, director, year, premiere, url_trailer, url_poster_vertical, url_poster_horizontal, time, age, story, rating, studio_id, language_id) " + 
                "VALUES (:movie_name, :director, :year, :premiere, :url_trailer, :url_poster_vertical, :url_poster_horizontal, :time, :age, :story, :rating, :studio_id, :language_id)",
        nativeQuery = true
    )
    void saveMovie(
        @Param("movie_name") String movieName,
        @Param("director") String director,
        @Param("year") Integer year,
        @Param("premiere") LocalDate premiere,
        @Param("url_trailer") String urlTrailer,
        @Param("url_poster_vertical") String urlVerticalPoster,
        @Param("url_poster_horizontal") String urlHorizontalPoster,
        @Param("time") LocalTime time,
        @Param("age") Integer age,
        @Param("story") String story,
        @Param("rating") int rating,
        @Param("studio_id") Integer studioId,
        @Param("language_id") Integer languageId
    );
}
