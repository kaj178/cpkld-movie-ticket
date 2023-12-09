package com.cpkld.service.impl;

import com.cpkld.dto.TicketDTO;
import com.cpkld.model.entity.*;
import com.cpkld.model.exception.notfound.TicketNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.*;
import com.cpkld.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieGenreRepository movieGenreRepository;
    @Autowired
    private TheaterRepository theaterRepository;

    @Override
    public ResponseEntity<?> getAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                tickets.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getAllTicketsByShowTimeId(Integer showtimeId) {
        Optional<List<Ticket>> optional = ticketRepository.getTicketsByShowTimeId(showtimeId);
        if (optional.isEmpty()) {
            throw new TicketNotFoundException("Ticket not founded!");
        }
        List<Ticket> tickets = optional.get();
        return new ResponseEntity<>(
            new ApiResponse<> (
                HttpStatus.OK.value(),
                "Success",
                tickets.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getAllTicketsByBookingId(Integer bookingId) {
        Optional<List<Ticket>> optional = ticketRepository.getTicketsByBookingId(bookingId);
        if (optional.isEmpty()) {
            throw new TicketNotFoundException("Ticket not founded!");
        }
        List<Ticket> tickets = optional.get();
        return new ResponseEntity<>(
            new ApiResponse<> (
                HttpStatus.OK.value(),
                "Success",
                tickets.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    private TicketDTO convertEntityToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();

        Optional<List<Seat>> optionalSeats = seatRepository.getSeatByTicket(ticket.ticketId);

        Movie movie = movieRepository.findMovieByTicketId(ticket.ticketId);

        List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenreByMovieId(movie.getMovieId());

        Theater theater = theaterRepository.getTheaterByTicketId(1);

        LocalDateTime time = ticket.getShowTime().getStartTime();

        List<MenuBooking> menuBookings = ticket.getBooking().getMenuBookings();

        StringBuilder strSeats = new StringBuilder();
        StringBuilder strCombos = new StringBuilder();
        if (optionalSeats.isPresent()) {
            List<Seat> seats = optionalSeats.get();
            for (Seat seat : seats) {
                strSeats.append(seat.getSeatName()).append(", ");
            }
        }
        if (menuBookings == null) {
            strCombos.append("Không có");
        } else {
            for (MenuBooking menuBooking : menuBookings) {
                if (menuBooking.getMenu() != null) {
                    if (menuBookings.size() > 1) {
                        strCombos.append(menuBooking.getMenu().getName()).append(", ");
                    } 
                    strCombos.append(menuBooking.getMenu().getName());
                } else { 
                    strCombos.append("Không có");
                }
            }
        }
        ticketDTO.setTicketId(ticket.ticketId);
        ticketDTO.setShowTimeID(ticket.getShowTime().getId());
        ticketDTO.setStatusId(1);
        ticketDTO.setBookingId(ticket.getBooking().getBookingId());
        ticketDTO.setCustomerEmail(ticketRepository.getEmailCustomerByTicket(ticket.ticketId));
        ticketDTO.setMovie(movie);
        ticketDTO.setAge(movie.getAge());

        StringBuilder strNameGenre = new StringBuilder();
        for (MovieGenre item : movieGenres) {
            strNameGenre.append(item.getName()).append(", ");
        }

        ticketDTO.setType(String.valueOf(strNameGenre));
        ticketDTO.setTheaterName(theater.getName());
        ticketDTO.setStartTime(time.toLocalTime());
        ticketDTO.setDateTime(time.toLocalDate());
        ticketDTO.setSeatsId(ticket.getSeat().getSeatId());
        ticketDTO.setCombo(String.valueOf(strCombos));
        ticketDTO.setTotalPrice(ticket.getBooking().getTotalPrice());
        return ticketDTO;
    }
}
