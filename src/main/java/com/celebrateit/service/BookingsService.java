package com.celebrateit.service;



import java.util.List;

import com.celebrateit.dto.BookingsBillDTO;
import com.celebrateit.dto.BookingsDisplayDTO;
import com.celebrateit.dto.BookingsRequestDTO;
import com.celebrateit.dto.BookingsShowDTO;
import com.celebrateit.pojo.BookingStatus;




public interface BookingsService {
    int addBooking(BookingsRequestDTO dto);
    BookingsBillDTO getBookingDetailsByBookingId(int bookingId);
    
    //TRUPTI
    public List<BookingsShowDTO> getBookingsByCategoryId(int categoryId);
    public List<BookingsDisplayDTO> getAllBookings();
    //String cancelBookingById(Integer bookingId);
    public String cancelBookingById(int bookingId);
    
    public List<BookingsDisplayDTO> getBookingsByStatus(BookingStatus status);  //user side
    public List<BookingsShowDTO> getAllBookingsByStatus(BookingStatus status);   //admin side
    public String completedBookingById(int bookingId);
    
    public List<BookingsDisplayDTO> getBookingsByUserId(int userId);
}
