package com.cpkld.service.impl;

import com.cpkld.dto.AnnualRevenueDTO;
import com.cpkld.dto.BookingDTO;
import com.cpkld.dto.MonthlyRevenueDTO;
import com.cpkld.dto.QuarterlyRevenueDTO;
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
    public ResponseEntity<?> statisticBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        List<AnnualRevenueDTO> annualRevenueDTOS = new ArrayList<>();
        for (int year = 2015; year<=2023; year++) {
            AnnualRevenueDTO annualRevenueDTO = new AnnualRevenueDTO();
            annualRevenueDTO.setYear(year);

            double totalRevenueYear = 0.0;

            List<QuarterlyRevenueDTO> quarterlyRevenueDTOS = new ArrayList<>();
            for (int quarter=1; quarter<=4; quarter++) {
                QuarterlyRevenueDTO quarterlyRevenueDTO = new QuarterlyRevenueDTO();

                quarterlyRevenueDTO.setYear(year);
                quarterlyRevenueDTO.setQuarter(quarter);
                double totalRevenueQuarter = 0.0;

                List<MonthlyRevenueDTO> monthlyRevenueDTOS = new ArrayList<>();
                int countTime = 0;
                if (quarter == 1) {
                    countTime = 1;
                } else if (quarter == 2) {
                    countTime = 4;
                } else if (quarter == 3) {
                    countTime = 7;
                } else {
                    countTime = 10;
                }
                for (int month = countTime; month<countTime+3; month++) {
                    //set data
                    MonthlyRevenueDTO monthlyRevenueDTO = new MonthlyRevenueDTO();
                    monthlyRevenueDTO.setMonth(month);
                    monthlyRevenueDTO.setQuarterly(quarter);
                    monthlyRevenueDTO.setYear(year);
                    int amountInvoice = 0;
                    double totalRevenue = 0.0;

                    for (Booking item : bookings) {

                        LocalDateTime period = item.getBookingTime();
                        int periodMonth = period.getMonthValue();
                        int periodYear = period.getYear();

                        if (periodYear == year && periodMonth == month)  {
                            amountInvoice += 1;
                            totalRevenue += item.getTotalPrice();
                        }
                    }
                    monthlyRevenueDTO.setTotalRevenue(totalRevenue);
                    monthlyRevenueDTO.setAmountInvoice(amountInvoice);
                    monthlyRevenueDTOS.add(monthlyRevenueDTO);
                    quarterlyRevenueDTO.setMonthlyRevenueDTOS(monthlyRevenueDTOS);
                    totalRevenueQuarter += totalRevenue;
                }
                totalRevenueYear += totalRevenueQuarter;
                quarterlyRevenueDTO.setTotalRevenue(totalRevenueQuarter);
                quarterlyRevenueDTOS.add(quarterlyRevenueDTO);

            }
            annualRevenueDTO.setTotalRevenue(totalRevenueYear);
            annualRevenueDTO.setQuarterlyRevenueDTOS(quarterlyRevenueDTOS);
            annualRevenueDTOS.add(annualRevenueDTO);
        }
        return new ResponseEntity<>(
                new ApiResponse<> (
                    HttpStatus.OK.value(),
                        "Success",
                        annualRevenueDTOS
                ),
                HttpStatus.OK
        );
    }

    private BookingDTO convertEntityToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setTotalPrice(booking.getTotalPrice());
        bookingDTO.setStartTime(booking.getBookingTime());
        return bookingDTO;
    }

}
