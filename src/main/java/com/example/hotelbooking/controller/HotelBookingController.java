package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.HotelBooking;
import com.example.hotelbooking.repository.HotelBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class HotelBookingController
{
	@Autowired
	HotelBookingRepository hotelBookingRepo;

	@GetMapping("/getAllReservations")
	public ResponseEntity<List<HotelBooking>> getAllReservations()
	{

		try
		{
			List<HotelBooking> listofReservations = new ArrayList<>();
			hotelBookingRepo.findAll().forEach(listofReservations::add);

			if(listofReservations.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception ex)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/createReservation")
	public ResponseEntity<HotelBooking> createReservation(@RequestBody HotelBooking hotelBooking)
	{
		HotelBooking hotelBookingObject = hotelBookingRepo.save(hotelBooking);

		return new ResponseEntity<>(hotelBookingObject, HttpStatus.OK);
	}

	@PostMapping("/updateReservation/{id}")
	public ResponseEntity<HotelBooking> updateReservation(@PathVariable Long id, @RequestBody HotelBooking updatedReservationData)
	{
		Optional<HotelBooking> previousReservationDate = hotelBookingRepo.findById(id);

		if(previousReservationDate.isPresent())
		{
			HotelBooking newReservationData = previousReservationDate.get();
			newReservationData.setName(updatedReservationData.getName());
			newReservationData.setCellphoneNumber(updatedReservationData.getCellphoneNumber());
			newReservationData.setEmailAddress(updatedReservationData.getEmailAddress());
			newReservationData.setNameOfHotel(updatedReservationData.getNameOfHotel());
			newReservationData.setCheckInDate(updatedReservationData.getCheckInDate());
			newReservationData.setCheckInDate(updatedReservationData.getCheckOutDate());

			HotelBooking hotelBookingObject = hotelBookingRepo.save(newReservationData);
			return new ResponseEntity<>(hotelBookingObject, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("/deleteReservation/{id}")
	public ResponseEntity<HttpStatus> deleteReservation(@PathVariable Long id)
	{
		hotelBookingRepo.deleteById(id);
		return new ResponseEntity<>( HttpStatus.OK);
	}
}
