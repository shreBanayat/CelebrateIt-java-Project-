package com.celebrateit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.celebrateit.dto.BookingsBillDTO;
import com.celebrateit.dto.BookingsDisplayDTO;
import com.celebrateit.dto.BookingsRequestDTO;
import com.celebrateit.dto.BookingsShowDTO;
import com.celebrateit.pojo.BookingStatus;
import com.celebrateit.pojo.Bookings;
import com.celebrateit.service.BookingsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:3000")

public class BookingsController {

	@Autowired
    private BookingsService bookingsService;

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody BookingsRequestDTO dto) {
        int bookingId = bookingsService.addBooking(dto);
        if (bookingId != -1) {
            return ResponseEntity.ok(bookingId);
        } else {
            return ResponseEntity.badRequest().body("Failed to place order");
        }
    }
    
    //BookingId
    @GetMapping("/GetBookingDetailsByBookingId/{bookingId}")
    public ResponseEntity<BookingsBillDTO> getBookingDetailsByBookingId(@PathVariable int bookingId) {
        BookingsBillDTO booking = bookingsService.getBookingDetailsByBookingId(bookingId);
        return booking != null ? ResponseEntity.ok(booking) :
                ResponseEntity.notFound().build();
    }
    
//    //categoryid trupti
//    @GetMapping("/GetBookingsByCategoryId/{categoryId}")
//    public ResponseEntity<BookingsShowDTO> getBookingsByCategoryId(@PathVariable int categoryId) {
//    	 BookingsShowDTO booking = bookingsService.getBookingsByCategoryId(categoryId);
//    	 return booking != null ? ResponseEntity.ok(booking) :
//             ResponseEntity.notFound().build();
//    }
    
    //getAllBookings
    @GetMapping("/getAllBookings")
    public ResponseEntity<List<BookingsDisplayDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingsService.getAllBookings());
    }
    
    //cancel
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable int bookingId) {
        try {
            bookingsService.cancelBookingById(bookingId);
            return ResponseEntity.ok("Booking cancelled successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    //BookingDetailsByBookingStatus
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getBookingsByStatus(@PathVariable String status) {
        try {
            BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
            List<BookingsDisplayDTO> bookings = bookingsService.getBookingsByStatus(bookingStatus);
            return ResponseEntity.ok(bookings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid booking status. Valid statuses: CONFIRMED, CANCELLED, PENDING, etc.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


  //BookingDetailsByBookingStatus  on admin side
    
    @GetMapping("/adminDasboardStatus/{status}")
    public ResponseEntity<?> getAllBookingsByStatus(@PathVariable String status) {
        try {
            BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
            List<BookingsShowDTO> bookings = bookingsService.getAllBookingsByStatus(bookingStatus);
            return ResponseEntity.ok(bookings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid booking status. Valid statuses: CONFIRMED, CANCELLED, PENDING, etc.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    //Update Complted Booking
    @PutMapping("/UpdateBookingStatusTocompleted/{bookingId}")
    public ResponseEntity<String> completedBooking(@PathVariable int bookingId) {
        try {
            bookingsService.completedBookingById(bookingId);
            return ResponseEntity.ok("Booking completed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    //GetBookingDetailsByUserId
    @GetMapping("/GetBookingsByUserId/{userId}")
    public ResponseEntity<List<BookingsDisplayDTO>> getBookingsByUserId(@PathVariable int userId) {
    	List<BookingsDisplayDTO> bookings = bookingsService.getBookingsByUserId(userId);
    	 return bookings != null ? ResponseEntity.ok(bookings) :
             ResponseEntity.notFound().build();
    }
    
    //categoryid 
    @GetMapping("/GetBookingsByCategoryId/{categoryId}")
    public ResponseEntity<List<BookingsShowDTO>> getBookingsByCategoryId(@PathVariable int categoryId) {
    	List <BookingsShowDTO> booking = bookingsService.getBookingsByCategoryId(categoryId);
    	 return booking != null ? ResponseEntity.ok(booking) :	
             ResponseEntity.notFound().build();
    }
}
