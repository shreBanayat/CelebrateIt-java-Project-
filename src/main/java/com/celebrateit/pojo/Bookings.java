package com.celebrateit.pojo;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "service_bookings")
public class Bookings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingid;

    @Column(name = "event_location", length = 255, nullable = false)
    private String eventLocation;

    @Column(name = "pin_code", length = 10, nullable = false)
    private String pinCode;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventdate;

    @Column(name = "events_details", length = 1000, nullable = false)
    private String eventdetails;

    @Column(name = "quantity", nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer quantity = 1;

    @Column(name = "total_price", nullable = false, columnDefinition = "DECIMAL(18,2)")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20, nullable = false)
    private PaymentMethod paymentmethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", length = 20, nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'CONFIRMED'")
    private BookingStatus bookingstatus=BookingStatus.CONFIRMED;

    // Foreign Key for User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Foreign Key for Facility
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;

    // Foreign Key for Category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

}
