package com.celebrateit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.celebrateit.pojo.BookingStatus;
import com.celebrateit.pojo.Bookings;

public interface BookingsDao extends JpaRepository<Bookings, Integer> {

	List<Bookings> findByBookingstatus(BookingStatus status);
	
	@Query("SELECT b FROM Bookings b WHERE b.user.userid = ?1")
    List<Bookings> findByUserId(int userId);
	
	@Query("SELECT b FROM Bookings b WHERE b.category.categorieid=?1")
	List<Bookings> findByCategoryId(int categoryId);
	

}
