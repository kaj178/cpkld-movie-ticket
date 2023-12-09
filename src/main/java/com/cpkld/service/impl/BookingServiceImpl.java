package com.cpkld.service.impl;

import com.cpkld.dto.AnnualRevenueDTO;
import com.cpkld.dto.BookingDTO;
import com.cpkld.dto.MonthlyRevenueDTO;
import com.cpkld.dto.QuarterlyRevenueDTO;
import com.cpkld.model.entity.*;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.BookingRepository;
import com.cpkld.repository.CustomerRepository;
import com.cpkld.repository.MenuBookingRepository;
import com.cpkld.repository.PromotionRepository;
import com.cpkld.repository.TicketRepository;
import com.cpkld.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MenuBookingRepository menuBookingRepository;

    @Override
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        bookingRepository.findAll().stream().map(this::convertEntityToDTO)
                                .collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getBookingByCustomerID(int id) {
        List<Booking> bookings = bookingRepository.getBookingByCustomerID(id);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        bookings.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
                HttpStatus.OK);
    }

    // int time => 1/2/3 => thang/quy/nam
    @Override
    public ResponseEntity<?> statisticBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        List<AnnualRevenueDTO> annualRevenueDTOS = new ArrayList<>();
        for (int year = 2022; year <= 2024; year++) {
            AnnualRevenueDTO annualRevenueDTO = new AnnualRevenueDTO();
            annualRevenueDTO.setYear(year);

            double totalRevenueYear = 0.0;

            List<QuarterlyRevenueDTO> quarterlyRevenueDTOS = new ArrayList<>();
            for (int quarter = 1; quarter <= 4; quarter++) {
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
                for (int month = countTime; month < countTime + 3; month++) {
                    // set data
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

                        if (periodYear == year && periodMonth == month) {
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
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        annualRevenueDTOS),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        booking.setAmount(bookingDTO.getAmountItem());
        booking.setBookingTime(bookingDTO.getBookingTime());
        booking.setStatus(String.valueOf(bookingDTO.getStatus()));
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setPromotion(!bookingDTO.getPromotionName().equals("")
                ? promotionRepository.findPromotionByName(bookingDTO.getPromotionName()).get()
                : null);
        booking.setCustomer(customerRepository.findById(bookingDTO.getCustomerId()).get());
        bookingRepository.saveBooking(
                booking.getAmount(),
                booking.getBookingTime(),
                "1",
                booking.getTotalPrice(),
                booking.getPromotion() != null ? booking.getPromotion().getId() : null,
                booking.getCustomer().getId());
        for (Seat seat : bookingDTO.getSeats()) {
            if (bookingRepository.findById(bookingRepository.getLastId()) != null) {
                ticketRepository.saveTicket(
                        bookingRepository.getLastId(),
                        seat.getSeatId(),
                        bookingDTO.getShowTimeId(),
                        bookingDTO.getStatus());
            }
        }
        for (Menu menu : bookingDTO.getMenus()) {
            if (bookingRepository.findById(bookingRepository.getLastId()) != null) {
                menuBookingRepository.saveMenuBooking(bookingRepository.getLastId(), menu.getMenuId());
            }
        }
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "Success", null),
                HttpStatus.OK);
    }

    private BookingDTO convertEntityToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setTotalPrice(booking.getTotalPrice());
        bookingDTO.setStartTime(booking.getBookingTime());
        bookingDTO.setBookingTime(booking.getBookingTime());
        bookingDTO.setBookingId(booking.bookingId);
        bookingDTO.setCustomerId(booking.getCustomer().getId());

        List<Ticket> tickets = ticketRepository.getTicketsByBookingId(booking.getBookingId()).get();
        for (Ticket ticket : tickets) {
            List<Seat> seats = new ArrayList<>();
            seats.add(ticket.getSeat());
            bookingDTO.setShowTimeId(ticket.getShowTime().getId());
            bookingDTO.setSeats(seats);
            break;
        }
        bookingDTO.setAmountItem(booking.amount);
        bookingDTO.setStatus(Integer.parseInt(booking.getStatus()));

        String promotionName = "";
        Promotion promotion = booking.getPromotion();
        if (promotion != null) {
            promotionName = promotion.getName();
        } else {
            promotionName = "Khong co";
        }
        bookingDTO.setPromotionName(promotionName);

        List<MenuBooking> menuBookingList = booking.getMenuBookings();
        List<Menu> menuList = new ArrayList<>();
        for (MenuBooking mennuBooking : menuBookingList) {
            menuList.add(mennuBooking.getMenu());
        }
        bookingDTO.setMenus(menuList);
        return bookingDTO;
    }

}
