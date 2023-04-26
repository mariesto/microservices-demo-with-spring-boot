package com.mariesto.ratingservice.controller;

import java.util.List;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.mariesto.ratingservice.persistence.domain.RatingDto;
import com.mariesto.ratingservice.persistence.entity.Rating;
import com.mariesto.ratingservice.service.RatingService;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingService service;

    @GetMapping("/")
    public List<Rating> findAllRatings(){
        return service.findAllRatings();
    }

    @GetMapping("/{bookId}")
    public List<Rating> findRatingsByBookId(@PathVariable String bookId){
        return service.findRatingsByBookId(bookId);
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createRating(@RequestBody RatingDto ratingDto){
        service.createRating(ratingDto);
    }

    @PostMapping("/simulate-error")
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public void simulateErrorCreateRating(@RequestBody RatingDto ratingDto) throws HttpException {
        throw new HttpException("service unavailable");
    }

    @DeleteMapping("/{ratingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable Long ratingId){
        service.deleteRating(ratingId);
    }

}
