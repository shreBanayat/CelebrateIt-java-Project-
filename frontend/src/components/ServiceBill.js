import React, { useRef, useState } from "react";
import { Container, Row, Col, Table, Button } from "react-bootstrap";
import { useReactToPrint } from "react-to-print";
import "../assets/style/ServiceBill.css";  // Ensure this path is correct
import { useLocation,useNavigate } from "react-router-dom";
import  { useEffect } from 'react';
import axios from 'axios';
import { ArrowLeft } from 'react-bootstrap-icons'; // Import Arrow Icon

const ServiceBill = () => {
    const [Bookings,setBooking]=useState(
        {
            "bookingid": 0,
            "eventLocation": "",
            "pinCode": "",
            "eventdate": "",
            "quantity": 1,
            "totalPrice": 0,
            "bookingstatus": "COMPLETED",
            "paymentmethod": "CARD",
            "uemail": "",
            "ucontact_number": "",
            "stitle": "",
            "sbasePrice": 0,
            "sdiscount": 0,
            "bcategoryname": "",
            "uname": ""
          }

    );
    const location = useLocation();

    const navigate = useNavigate();  // Initialize useNavigate

    const bookingId = location.state;

    const { userDetails, facilityDetails, totalPrice, discountAmount, paymentMethod } =
      location.state || {};

    const invoiceRef = useRef(); // Ref for printing

    const handlePrint = useReactToPrint({
        content: () => invoiceRef.current, // Target only invoice content
        documentTitle: "Service Bill",
        onAfterPrint: () => console.log("Print successful"),
    });

    const handlePrintFallback = () => {
        handlePrint();  // Try using react-to-print first
        setTimeout(() => {
            window.print(); // Fallback to window.print() after a short delay
        }, 500);
    };
 
    const fetchData = async () =>{
        try {
          //console.log(orderId);
          const response = await axios.get(`http://localhost:8080/bookings/GetBookingDetailsByBookingId/${bookingId}`);
          setBooking(response.data);
          console.log(response.data);
        } catch (error) {
          console.error(error.message);
        }
      }
    
      useEffect(()=>{
        fetchData();
      }
      ,[])
    
    


    return (
        <div>

            {/* Back Button */}
            <Button variant="light" className="back-button" onClick={() => navigate("/Dashboard")}>
                <ArrowLeft size={20} /> Back to Dashboard
            </Button>

            {/* Invoice Content - Only this will be printed */}
            <Container ref={invoiceRef} className="invoice-container border p-4 bg-light">
                <Row className="mb-3">
                    <Col md={6}>
                        <h1 className="fw-bold">Invoice</h1>
                    </Col>
                    <Col md={6} className="text-end">
                        <h5>CelebrateIt</h5>
                        <p>
                            Street Address Line 01<br />
                            Street Address Line 02<br />
                            +1 (999)-999-9999<br />
                            Email Address<br />
                            Website
                        </p>
                    </Col>
                </Row>

                <Row>
                    <Col md={6}>
                        <h5>Bill To:</h5>
                        <p>
                            <strong>UserName:</strong>{Bookings.uname}<br />
                            <strong>Email:</strong>{Bookings.uemail}<br />
                            <strong>ContactNumber:</strong>{Bookings.ucontact_number}
                        </p>
                    </Col>
                    <Col md={6} className="text-end">
                        <h5>Event Details</h5>
                        <p>
                            <strong>EventLocation:</strong>{Bookings.eventLocation}<br />
                            <strong>PinCode:</strong>{Bookings.pinCode}<br/>
                            <strong>Date:</strong> {Bookings.eventdate}
                        </p>
                    </Col>
                </Row>

                <Table striped bordered hover className="mt-3">
                    <thead>
                        <tr>
                            <th>CategoryName</th>
                            <th>Title</th>
                            <th>Quantity</th>
                            <th>Base Price</th>
                            <th>Discount</th>
                            <th>Total Price</th>
                            <th>PaymentMethod</th>
                            <th>Booking status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>{Bookings.bcategoryname}</td>
                            <td>{Bookings.stitle}</td>
                            <td>{Bookings.quantity}</td>
                            <td>{Bookings.sbasePrice}</td>
                            <td>{Bookings.sdiscount}</td>
                            <td>{Bookings.totalPrice}</td>
                            <td>{Bookings.paymentmethod}</td>
                            <td>{Bookings.bookingstatus}</td>
                        </tr>
                        
                    </tbody>
                </Table>

                <Row className="mt-3">
                    <Col md={6}>
                        <h5>Terms & Conditions</h5>
                        <p>Payment due within 30 days. Late fees may apply.</p>
                    </Col>
                    <Col md={6} className="text-end">
                        <h5>Summary</h5>
                        <p>
                            <strong>Base Price:</strong>{Bookings.sbasePrice}<br />
                            <strong>Discount:</strong>{Bookings.sdiscount}<br />
                            <strong>Total:</strong> {Bookings.totalPrice}
                        </p>
                    </Col>
                </Row>

                <Row className="mt-4 text-center">
                    <Col>
                        <h5>Thank You for Choosing CelebrateIt!</h5>
                    </Col>
                </Row>
            </Container>

            {/* Print Button - This will NOT be printed */}
            <Row className="mt-4 text-center no-print">
                <Col>
                    <Button variant="primary" onClick={handlePrintFallback}>Print Bill</Button>
                </Col>
            </Row>
        </div>
    );
};

export default ServiceBill;  // Ensure correct export






