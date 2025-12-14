package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        //Generate unique userid
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        // implement rating service call :Using REST TEMPLATE
        return userRepository.findAll();
    }
// get Single user
    @Override
    public User getUser(String userId) {
        // get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given id is not found on the server!! : "+ userId));
        //fetch rating of the above user from rating service
       Rating[] ratingsOfUser =  restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);
       logger.info("{} ",ratingsOfUser);

       List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

       List<Rating> ratingList =  ratings.stream().map(rating ->{
           // api call to hotel service to get the hotel
           ResponseEntity<Hotel> forEntity  = restTemplate.getForEntity("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
           Hotel hotel = forEntity.getBody();
           logger.info("response status code : {} ",forEntity.getStatusCode());
           // set the hotel to rating
           rating.setHotel(hotel);
           // return the rating
           return rating;
       }).collect(Collectors.toList());
       user.setRatings(ratingList);
        return user;
    }
}
