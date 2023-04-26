package com.mariesto.ratingservice.persistence.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RatingDto implements Serializable {


    private String bookId;

    private int stars;

}
