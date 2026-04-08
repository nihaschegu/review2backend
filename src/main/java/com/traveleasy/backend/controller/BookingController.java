package com.traveleasy.backend.controller;

import com.traveleasy.backend.model.Booking;
import com.traveleasy.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000") // allow frontend
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    // GET all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // POST new booking
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        // Optional: Set default status if not provided
        if (booking.getStatus() == null || booking.getStatus().isEmpty()) {
            booking.setStatus("pending");
        }
        return bookingRepository.save(booking);
    }
}