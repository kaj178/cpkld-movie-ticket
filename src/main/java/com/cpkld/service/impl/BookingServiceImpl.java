package com.cpkld.service.impl;

import com.cpkld.dto.BookingDTO;
import com.cpkld.model.entity.Booking;
import com.cpkld.model.entity.MenuBooking;
import com.cpkld.model.entity.Seat;
import com.cpkld.model.entity.Ticket;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.BookingRepository;
import com.cpkld.repository.SeatRepository;
import com.cpkld.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SeatRepository seatRepository;
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

        return bookingDTO;
    }

}
