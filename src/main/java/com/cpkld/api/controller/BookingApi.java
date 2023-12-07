package com.cpkld.api.controller;

import com.cpkld.dto.BookingDTO;
import com.cpkld.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingApi {
    // public class BookingTemp {
    //     private int NumberOfTickets;
    //     private String BookingTime;
    //     private String Voucher;
    //     private int customer;
    //     private int ShowTimeID;
    //     private double TotalPrice;
    //     private List<Ticket> ListTicket;
    //     private List<Menu> ListMenu;

    //     // Getter and Setter for NumberOfTickets
    //     public int getNumberOfTickets() {
    //         return NumberOfTickets;
    //     }

    //     public void setNumberOfTickets(int numberOfTickets) {
    //         NumberOfTickets = numberOfTickets;
    //     }

    //     // Getter and Setter for BookingTime
    //     public String getBookingTime() {
    //         return BookingTime;
    //     }

    //     public void setBookingTime(String bookingTime) {
    //         BookingTime = bookingTime;
    //     }

    //     // Getter and Setter for Voucher
    //     public String getVoucher() {
    //         return Voucher;
    //     }

    //     public void setVoucher(String voucher) {
    //         Voucher = voucher;
    //     }

    //     // Getter and Setter for customer
    //     public int getCustomer() {
    //         return customer;
    //     }

    //     public void setCustomer(int customer) {
    //         this.customer = customer;
    //     }

    //     // Getter and Setter for ShowTimeID
    //     public int getShowTimeID() {
    //         return ShowTimeID;
    //     }

    //     public void setShowTimeID(int showTimeID) {
    //         ShowTimeID = showTimeID;
    //     }

    //     // Getter and Setter for TotalPrice
    //     public double getTotalPrice() {
    //         return TotalPrice;
    //     }

    //     public void setTotalPrice(double totalPrice) {
    //         TotalPrice = totalPrice;
    //     }

    //     // Getter and Setter for ListTicket
    //     public List<Ticket> getListTicket() {
    //         return ListTicket;
    //     }

    //     public void setListTicket(List<Ticket> listTicket) {
    //         ListTicket = listTicket;
    //     }

    //     // Getter and Setter for ListMenu
    //     public List<Menu> getListMenu() {
    //         return ListMenu;
    //     }

    //     public void setListMenu(List<Menu> listMenu) {
    //         ListMenu = listMenu;
    //     }
    // }

    @Autowired
    private BookingService bookingService;

    @GetMapping(params = "customer_id")
    public ResponseEntity<?> getBookingByID(@RequestParam("customer_id") Integer id) {
        return bookingService.getBookingByCustomerID(id);
    }

    @GetMapping
    public ResponseEntity<?> readAllBooking() {
        return bookingService.getAll();
    }

    @GetMapping("/statistic")
    public ResponseEntity<?> getStatistic() {
        return bookingService.statisticBookings();
    }

    @PostMapping //(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bookingDTO(@RequestBody BookingDTO bookingDTO) {
        return bookingService.add(bookingDTO);
    }
}
