package com.cpkld.service.impl;

import com.cpkld.dto.BookingDTO;
import com.cpkld.model.entity.Booking;
import com.cpkld.model.entity.MenuBooking;
import com.cpkld.model.entity.Seat;
import com.cpkld.model.entity.Ticket;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.BookingRepository;
import com.cpkld.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Override
    public ResponseEntity<?> getAll() {
        List<Booking> bookings = bookingRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        bookings.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
                ),
                HttpStatus.OK
        );
    }

    //int time => 1/2/3 => thang/quy/nam
    public ResponseEntity<?> statisticBookings(int time) {
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> bookingsResp = new ArrayList<>();
        LocalDateTime dateTimeResp = null;
        int amountReceiptResp = 0;
        double revenue = 0.0;
        if (time == 1) {
            int currentMonth = YearMonth.now().getMonthValue();
            for (Booking item : bookings) {
                if (item.getBookingTime().getMonthValue() == currentMonth) {

                }
            }
        }

        return null;
    }

    private BookingDTO convertEntityToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        List<Ticket> tickets = booking.getTickets();
        List<MenuBooking> menuBookings = booking.getMenuBookings();
        StringBuilder movieName = new StringBuilder();
        StringBuilder seatName = new StringBuilder();
        StringBuilder combos = new StringBuilder();
        String formatName;
        Ticket firstTicket;
        LocalDateTime startTime;
        int age;
        if (tickets != null) {
            firstTicket = tickets.get(0);
            formatName = firstTicket.getShowTime().getFormat().getName();
            age = firstTicket.getShowTime().getMovie().getAge();
            startTime = firstTicket.getShowTime().getStartTime();
            for (Ticket item : tickets) {
                seatName.append(item.getSeat().getSeatName()).append(", ");
                movieName.append(item.getShowTime().getMovie().getName()).append(", ");

            }
            bookingDTO.setTicketId(booking.bookingId);
            bookingDTO.setEmail(booking.getCustomer().getEmail());
            bookingDTO.setMovieName(String.valueOf(movieName));
            bookingDTO.setSeats(String.valueOf(seatName));

            bookingDTO.setAge(age);
            bookingDTO.setFormat(formatName);
            bookingDTO.setStartTime(startTime);
            bookingDTO.setTheaterName(firstTicket.getSeat().getRoom().getTheater().getName());

            bookingDTO.setTotalPrice(booking.getTotalPrice());

            for (MenuBooking item : menuBookings) {
                combos.append(item.getMenu().getName()).append(", ");
            }
            bookingDTO.setCombo(String.valueOf(combos));
        }

        return bookingDTO;
    }

}
