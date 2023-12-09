package com.cpkld.service.impl;

import com.cpkld.dto.ShowTimeDTO;
import com.cpkld.model.entity.*;
import com.cpkld.model.exception.notfound.ShowTimeNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.*;
import com.cpkld.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {
    @Autowired
    private ShowTimeRepository showTimeRepository;
    @Autowired
    private FormatRepository formatRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public ResponseEntity<?> getAll() {
        List<ShowTime> showTimes = showTimeRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        showTimes.stream().map(this::convertEntityToDTO)
                                .collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getShowTimeById(Integer showTimeId) {
        Optional<ShowTime> optional = showTimeRepository.findById(showTimeId);
        if (optional.isEmpty()) {
            throw new ShowTimeNotFoundException("Showtime not found!");
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        optional.stream().map(this::convertEntityToDTO).toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllShowTimeByDate(int YYYYMMDD) {
        int year = YYYYMMDD / 10000;
        int month = (YYYYMMDD % 10000) / 100;
        int day = YYYYMMDD % 100;

        LocalDate localDate = LocalDate.of(year, month, day);

        LocalTime specificTimeStart = LocalTime.of(0, 0);
        LocalTime specificTimeEnd = LocalTime.of(23, 59);

        LocalDateTime localDateTimeStart = localDate.atTime(specificTimeStart).atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime localDateTimeEnd = localDate.atTime(specificTimeEnd).atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Optional<List<ShowTime>> optional = showTimeRepository.getAllShowTimeByDate(localDateTimeStart,
                localDateTimeEnd);
        if (optional.isEmpty()) {
            throw new ShowTimeNotFoundException("Showtime not found!");
        }
        List<ShowTime> showTimes = optional.get();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        showTimes.stream().map(this::convertEntityToDTO).toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getShowTimeByDateAndGenre(int YYYYMMDD, Integer genreId) {
        int year = YYYYMMDD / 10000;
        int month = (YYYYMMDD % 10000) / 100;
        int day = YYYYMMDD % 100;

        LocalDate localDate = LocalDate.of(year, month, day);

        LocalTime specificTimeStart = LocalTime.of(0, 0);
        LocalTime specificTimeEnd = LocalTime.of(23, 59);

        LocalDateTime localDateTimeStart = localDate.atTime(specificTimeStart).atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime localDateTimeEnd = localDate.atTime(specificTimeEnd).atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Optional<List<ShowTime>> optional = showTimeRepository.getShowTimeByDateAndGenre(localDateTimeStart,
                localDateTimeEnd, genreId);
        if (optional.isEmpty()) {
            throw new ShowTimeNotFoundException("Showtime not found!");
        }
        List<ShowTime> showTimes = optional.get();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        showTimes.stream().map(this::convertEntityToDTO).toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getShowTimeByMovieAndTheater(Integer movieId, Integer theaterId) {
        Optional<List<ShowTime>> optional = showTimeRepository.getShowTimeByMovieAndTheater(movieId, theaterId);
        if (optional.isEmpty()) {
            throw new ShowTimeNotFoundException("Showtime not found!");
        }
        List<ShowTime> showTimes = optional.get();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        showTimes.stream().map(this::convertEntityToDTO).toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getShowTimeByDateAndTheater(int YYYYMMDD, Integer theaterId) {
        int year = YYYYMMDD / 10000;
        int month = (YYYYMMDD % 10000) / 100;
        int day = YYYYMMDD % 100;

        LocalDate localDate = LocalDate.of(year, month, day);

        LocalTime specificTimeStart = LocalTime.of(0, 0);
        LocalTime specificTimeEnd = LocalTime.of(23, 59);

        LocalDateTime localDateTimeStart = localDate.atTime(specificTimeStart).atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime localDateTimeEnd = localDate.atTime(specificTimeEnd).atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Optional<List<ShowTime>> optional = showTimeRepository.getShowTimeByDateAndTheater(localDateTimeStart,
                localDateTimeEnd, theaterId);
        if (optional.isEmpty()) {
            throw new ShowTimeNotFoundException("Showtime not found!");
        }
        List<ShowTime> showTimes = optional.get();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        showTimes.stream().map(this::convertEntityToDTO).toList()),
                HttpStatus.OK);
    }

    public ResponseEntity<?> add(ShowTimeDTO payload) {
        ShowTime showtime = new ShowTime();
        showtime.setPrice(payload.getPrice());
        Format format = formatRepository.findById(payload.getFormatId()).get();
        showtime.setFormat(format);
        Room room = roomRepository.findById(payload.getRoomId()).get();
        showtime.setRoom(room);
        showtime.setEndTime(payload.getEndTime());
        showtime.setStartTime(payload.getStartTime());
        int movieID = Integer.parseInt(payload.getMovieID());
        Movie movie = movieRepository.findById(movieID).orElse(null);

        if (movie == null) {
            // Handle the case where the Movie entity is not found
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            HttpStatus.NOT_FOUND.value(),
                            "Movie not found",
                            null),
                    HttpStatus.NOT_FOUND);
        }
        showtime.setMovie(movie);
        showTimeRepository.saveShowtime(
                Integer.parseInt(payload.getMovieID()),
                payload.getPrice(),
                payload.getEndTime(),
                payload.getFormatId(),
                payload.getStartTime(),
                payload.getRoomId());

        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        null),
                HttpStatus.OK);
    }

    public ResponseEntity<?> put(Integer showtimeID, ShowTimeDTO payload) {
        ShowTime showtime = showTimeRepository.findById(showtimeID).get();
        showtime.setPrice(payload.getPrice());
        Format format = formatRepository.findById(payload.getFormatId()).get();
        showtime.setFormat(format);
        Room room = roomRepository.findById(payload.getRoomId()).get();
        showtime.setRoom(room);
        showtime.setEndTime(payload.getEndTime());
        showtime.setStartTime(payload.getStartTime());

        int movieID = Integer.parseInt(payload.getMovieID());
        Movie movie = movieRepository.findById(movieID).orElse(null);

        if (movie == null) {
            // Handle the case where the Movie entity is not found
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            HttpStatus.NOT_FOUND.value(),
                            "Movie not found",
                            null),
                    HttpStatus.NOT_FOUND);
        }
        showtime.setMovie(movie);

        showTimeRepository.save(showtime);
        List<ShowTime> showtimeList = new ArrayList<ShowTime>();
        showtimeList.add(showtime);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        showtimeList.stream().map(this::convertEntityToDTO).toList()),
                HttpStatus.OK);

    }

    public ShowTimeDTO convertEntityToDTO(ShowTime showTime) {
        ShowTimeDTO showTimeDTO = new ShowTimeDTO();
        Set<String> seatList = new HashSet<>();
        for (Seat seat : showTime.getRoom().getSeats()) {
            seatList.add(seat.getType());
        }
        showTimeDTO.setShowTimeId(showTime.getId());
        showTimeDTO.setStartTime(showTime.getStartTime());
        showTimeDTO.setEndTime(showTime.getEndTime());
        showTimeDTO.setPrice(showTime.getPrice());
        showTimeDTO.setMovie(showTime.getMovie());

        Room room = showTime.getRoom();
        showTimeDTO.setRoomId(room.getRoomId());

        Format format = showTime.getFormat();
        showTimeDTO.setFormatId(format.getId());

        return showTimeDTO;
    }
}
