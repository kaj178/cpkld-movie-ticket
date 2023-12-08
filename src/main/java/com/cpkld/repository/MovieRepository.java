package com.cpkld.repository;

import com.cpkld.model.entity.Movie;

import java.util.List;

import com.cpkld.model.entity.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    // @Query("SELECT m FROM Movie m JOIN FETCH m.language")\
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
    public int getLastId();
}
