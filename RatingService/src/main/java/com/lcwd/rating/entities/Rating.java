package com.lcwd.rating.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("user_ratings")
public class Rating {
    @Id
    //In MongoDB id byDefault automatic generate
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String feedback;
}
