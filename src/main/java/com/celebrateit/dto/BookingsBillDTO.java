package com.celebrateit.dto;

import java.time.LocalDate;

import com.celebrateit.pojo.BookingStatus;
import com.celebrateit.pojo.PaymentMethod;

import lombok.Data;

@Data
public class BookingsBillDTO {

    private Integer bookingid;
    private String eventLocation;
    private String pinCode;
    private LocalDate eventdate;
   // private String eventdetails;
    private Integer quantity ;
    private Double totalPrice;
    private BookingStatus bookingstatus;
    private PaymentMethod paymentmethod;
    
    private String uName;
    private String uemail;
    private String ucontact_number;
    
    private String stitle;
    private Double sbasePrice;
    private Double sdiscount;
    
    private String bcategoryname;

    // Default constructor
    public BookingsBillDTO() {
    }
}
