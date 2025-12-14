package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    //Create
    Rating create(Rating rating);
    //Get all rating
    List<Rating> getRatings();

    //get all by userId
    List<Rating> getRatingByUserId(String userId);

    // get all by hotelId
    List<Rating> getRatingByHotelId(String hotelId);
}
