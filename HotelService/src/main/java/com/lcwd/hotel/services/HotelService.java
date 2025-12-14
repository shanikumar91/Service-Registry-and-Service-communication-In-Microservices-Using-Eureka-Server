package com.lcwd.hotel.services;

import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //Create
    Hotel create (Hotel hotel);


    // getAll
    List<Hotel> getAll();

    //get single hotel
    Hotel get(String id);
}
