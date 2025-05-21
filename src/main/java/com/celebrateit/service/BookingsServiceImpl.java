package com.celebrateit.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import com.celebrateit.dao.*;
import com.celebrateit.dto.BookingsBillDTO;
import com.celebrateit.dto.BookingsDisplayDTO;
import com.celebrateit.dto.BookingsRequestDTO;
import com.celebrateit.dto.BookingsShowDTO;
import com.celebrateit.pojo.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW) // Ensures a fresh transaction
@RequiredArgsConstructor
@Slf4j
public class BookingsServiceImpl implements BookingsService {

    private final BookingsDao bookingsDao;
    private final UserDao userDao;
    private final ServiceDao servicesDao;
    private final CategoryDao categoriesDao;
    private final ModelMapper modelMapper;

    @Override
    public int addBooking(BookingsRequestDTO dto) {
        try {
            Users user = userDao.findById(dto.getUserid())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Services service = servicesDao.findById(dto.getServiceid())
                    .orElseThrow(() -> new RuntimeException("Service not found"));

            Categories category = categoriesDao.findById(dto.getCategorieid())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Bookings booking = modelMapper.map(dto, Bookings.class);
            booking.setUser(user);
            booking.setService(service);
            booking.setCategory(category);

            // Ensure we're saving a new entity, not updating an existing one
            booking.setBookingid(null);

            bookingsDao.save(booking);
            return booking.getBookingid();
        } catch (Exception e) {
            log.error("Error while adding booking: {}", e.getMessage(), e);
            return -1;
        }
    }
    
    
    //bookingId
    @Override
    public BookingsBillDTO getBookingDetailsByBookingId(int bookingId) {
        Bookings booking = bookingsDao.findById(bookingId).orElseThrow();
                //.map(booking -> modelMapper.map(booking, BookingsBillDTO.class));
        BookingsBillDTO dto = modelMapper.map(booking, BookingsBillDTO.class);
        dto.setUName(booking.getUser().getUsername());
        dto.setUemail(booking.getUser().getEmail());
        dto.setUcontact_number(booking.getUser().getContact_number());
        dto.setStitle(booking.getService().getTitle());
        dto.setSdiscount(booking.getService().getDiscount());
        dto.setSbasePrice(booking.getService().getBasePrice());
        dto.setBcategoryname(booking.getCategory().getCategoryname());
        
        return dto;
    }
    
    
//    //trupti Categoriid
//    @Override
//    public BookingsShowDTO getBookingsByCategoryId(int categoryId) {
//        Bookings booking = bookingsDao.findById(categoryId).orElseThrow();
//        
//        BookingsShowDTO dto = modelMapper.map(booking, BookingsShowDTO.class);
//        dto.setUName(booking.getUser().getUsername());
//        dto.setUemail(booking.getUser().getEmail());
//        dto.setUcontact_number(booking.getUser().getContact_number());
//        dto.setStitle(booking.getService().getTitle());
//        dto.setSdiscount(booking.getService().getDiscount());
//        dto.setSbasePrice(booking.getService().getBasePrice());
//        dto.setSimage(booking.getService().getImage());
//        dto.setBcategoryname(booking.getCategory().getCategoryname());
//        
//        return dto;
//    }
    
    //getallbookings
    @Override
    public List<BookingsDisplayDTO> getAllBookings() {
    	List<Bookings> bookings = bookingsDao.findAll();
    	
       
    	 return bookings.stream().map(booking -> {
    	        BookingsDisplayDTO dto = modelMapper.map(booking, BookingsDisplayDTO.class);
    	        dto.setStitle(booking.getService().getTitle());
    	        dto.setSdiscount(booking.getService().getDiscount());
    	        dto.setSbasePrice(booking.getService().getBasePrice());
    	        dto.setSimage(booking.getService().getImage());
    	        dto.setBcategoryname(booking.getCategory().getCategoryname());
    	        return dto;
    	    }).collect(Collectors.toList());
        //return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    
    //cancel
    @Override
    public String cancelBookingById(int bookingId) {
        Bookings booking = bookingsDao.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        // Set status to CANCELLED
        booking.setBookingstatus(BookingStatus.CANCELLED);
        bookingsDao.save(booking);

        log.info("Booking ID {} has been cancelled successfully.", bookingId);
        return "Booking with ID " + bookingId + " has been cancelled successfully.";
    }

   //GetBookingDetailsByBookingStatus
    @Override
    public List<BookingsDisplayDTO> getBookingsByStatus(BookingStatus status) {
        List<Bookings> bookings = bookingsDao.findByBookingstatus(status);
        
        if (bookings.isEmpty()) {
            throw new RuntimeException("No bookings found with status: " + status);
        }

   	 return bookings.stream().map(booking -> {
	        BookingsDisplayDTO dto = modelMapper.map(booking, BookingsDisplayDTO.class);
	        dto.setStitle(booking.getService().getTitle());
	        dto.setSdiscount(booking.getService().getDiscount());
	        dto.setSbasePrice(booking.getService().getBasePrice());
	        dto.setSimage(booking.getService().getImage());
	        dto.setBcategoryname(booking.getCategory().getCategoryname());
	        return dto;
	    }).collect(Collectors.toList());
    }
    
	 //GetBookingDetailsByBookingStatus on Admin Side
    @Override
    public List<BookingsShowDTO> getAllBookingsByStatus(BookingStatus status) {
        List<Bookings> bookings = bookingsDao.findByBookingstatus(status);
        
        if (bookings.isEmpty()) {
            throw new RuntimeException("No bookings found with status: " + status);
        }

   	 return bookings.stream().map(booking -> {
   		BookingsShowDTO dto = modelMapper.map(booking, BookingsShowDTO.class);
        dto.setUName(booking.getUser().getUsername());
        dto.setUemail(booking.getUser().getEmail());
        dto.setUcontact_number(booking.getUser().getContact_number());
        dto.setStitle(booking.getService().getTitle());
        dto.setSdiscount(booking.getService().getDiscount());
        dto.setSbasePrice(booking.getService().getBasePrice());
        dto.setSimage(booking.getService().getImage());
        dto.setBcategoryname(booking.getCategory().getCategoryname());
	    return dto;
	    }).collect(Collectors.toList());
    }
     
    //UpdateStatusToCompleted
    @Override
    public String completedBookingById(int bookingId) {
        Bookings booking = bookingsDao.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        // Set status to CANCELLED
        booking.setBookingstatus(BookingStatus.COMPLETED);
        bookingsDao.save(booking);

        log.info("Booking ID {} has been complted successfully.", bookingId);
        return "Booking with ID " + bookingId + " has been cancelled successfully.";
    }
    
    
    //GetBookingDetailsByUserId
    @Override
    public List<BookingsDisplayDTO> getBookingsByUserId(int userId) {
        List<Bookings> bookings = bookingsDao.findByUserId(userId);
        List<BookingsDisplayDTO> dtoList = new ArrayList<BookingsDisplayDTO>();
        
        for(Bookings booking: bookings) 
        {
	        BookingsDisplayDTO dto = modelMapper.map(booking, BookingsDisplayDTO.class);
	        dto.setStitle(booking.getService().getTitle());
	        dto.setSdiscount(booking.getService().getDiscount());
	        dto.setSbasePrice(booking.getService().getBasePrice());
	        dto.setSimage(booking.getService().getImage());
	        dto.setBcategoryname(booking.getCategory().getCategoryname());
	        dtoList.add(dto);
        }
        return dtoList;
    }
    
    
    //trupti Categoriid
//    @Override
//    public List<BookingsShowDTO> getBookingsByCategoryId(int categoryId) {
//    	
//    	List<Bookings> bookings = bookingsDao.findByCategoryId(categoryId);
//    	  List<BookingsShowDTO> dtoList = new ArrayList<BookingsShowDTO>();
//    	  
//    	  for(Bookings booking: bookings)
//    	  {
//        BookingsShowDTO dto = modelMapper.map(booking, BookingsShowDTO.class);
//        dto.setUName(booking.getUser().getUsername());
//        dto.setUemail(booking.getUser().getEmail());
//        dto.setUcontact_number(booking.getUser().getContact_number());
//        dto.setStitle(booking.getService().getTitle());
//        dto.setSdiscount(booking.getService().getDiscount());
//        dto.setSbasePrice(booking.getService().getBasePrice());
//        dto.setSimage(booking.getService().getImage());
//        dto.setBcategoryname(booking.getCategory().getCategoryname());
//    	  }
//        return dtoList;
//    }
    @Override
    public List<BookingsShowDTO> getBookingsByCategoryId(int categoryId) {
        List<Bookings> bookings = bookingsDao.findByCategoryId(categoryId);
        
        if (bookings.isEmpty()) {
            System.out.println("No bookings found for categoryId: " + categoryId);
        }

        List<BookingsShowDTO> dtoList = new ArrayList<>();
        for (Bookings booking : bookings) {
            BookingsShowDTO dto = modelMapper.map(booking, BookingsShowDTO.class);
            dto.setUName(booking.getUser().getUsername());
            dto.setUemail(booking.getUser().getEmail());
            dto.setUcontact_number(booking.getUser().getContact_number());
            dto.setStitle(booking.getService().getTitle());
            dto.setSdiscount(booking.getService().getDiscount());
            dto.setSbasePrice(booking.getService().getBasePrice());
            dto.setSimage(booking.getService().getImage());
            dto.setBcategoryname(booking.getCategory().getCategoryname());
            dtoList.add(dto);
        }
        return dtoList;
    }

	


    
    
}
