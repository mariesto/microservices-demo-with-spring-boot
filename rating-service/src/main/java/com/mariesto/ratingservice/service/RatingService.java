package com.mariesto.ratingservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mariesto.ratingservice.common.RatingNotFoundException;
import com.mariesto.ratingservice.persistence.domain.RatingDto;
import com.mariesto.ratingservice.persistence.entity.Rating;
import com.mariesto.ratingservice.persistence.repository.RatingRepository;

@Service
public class RatingService {
    @Autowired
    private RatingRepository repository;

    public List<Rating> findRatingsByBookId(String bookId) {
        return repository.findRatingsByBookId(bookId);
    }

    public List<Rating> findAllRatings() {
        return repository.findAll();
    }

    public Rating findRatingById(Long ratingId) throws RatingNotFoundException {
        return repository.findById(ratingId).orElseThrow(() -> new RatingNotFoundException("Rating Not Found with ID : " + ratingId));
    }

    public void createRating(RatingDto ratingDto) {
        Rating rating = new Rating();
        rating.setStars(ratingDto.getStars());
        rating.setBookId(ratingDto.getBookId());
        repository.save(rating);
    }

    public void deleteRating(Long ratingId) {
        repository.deleteById(ratingId);
    }

}
