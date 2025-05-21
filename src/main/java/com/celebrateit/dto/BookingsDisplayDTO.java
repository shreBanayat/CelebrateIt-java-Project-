package com.celebrateit.dto;

import java.time.LocalDate;
import com.celebrateit.pojo.BookingStatus;
import lombok.Data;

@Data
public class BookingsDisplayDTO {

    private Integer bookingid;
    private String eventLocation;
    private String pinCode;
    private LocalDate eventdate;
    private Double totalPrice;
    private BookingStatus bookingstatus;
    
    private String stitle;
    private Double sbasePrice;
    private Double sdiscount;
    private String simage;
    
    private String bcategoryname;

    public BookingsDisplayDTO() {
        // Default constructor
    }
}
