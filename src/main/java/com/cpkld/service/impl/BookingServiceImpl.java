//package com.cpkld.service.impl;
//
//import com.cpkld.model.entity.Booking;
//import com.cpkld.repository.BookingRepository;
//import com.cpkld.service.BookingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.YearMonth;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class BookingServiceImpl implements BookingService {
//    @Autowired
//    private BookingRepository bookingRepository;
//    @Override
//    public ResponseEntity<?> getAll() {
//        return null;
//    }
//
//    //int time => 1/2/3 => thang/quy/nam
//    public ResponseEntity<?> statisticBookings(int time) {
//        List<Booking> bookings = bookingRepository.findAll();
//        List<Booking> bookingsResp = new ArrayList<>();
//        LocalDateTime dateTimeResp = null;
//        int amountReceiptResp = 0;
//        double revenue = 0.0;
//        if (time == 1) {
//            int currentMonth = YearMonth.now().getMonthValue();
//            for (Booking item : bookings) {
//                if (item.getBookingTime().getMonthValue() == currentMonth) {
//
//                }
//            }
//        }
//
//
//    }
//
//
//}
