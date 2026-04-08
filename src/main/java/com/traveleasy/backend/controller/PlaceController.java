package com.traveleasy.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.traveleasy.backend.model.Place;
import com.traveleasy.backend.repository.PlaceRepository;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "http://localhost:3000")
public class PlaceController {

    @Autowired
    private PlaceRepository repo;

    @GetMapping
    public List<Place> getAllPlaces() {
        return repo.findAll();
    }

    @PostMapping
    public Place addPlace(@RequestBody Place place) {
        return repo.save(place);
    }

    @PutMapping("/{id}")
    public Place updatePlace(@PathVariable Long id, @RequestBody Place place) {
        place.setId(id);
        return repo.save(place);
    }

    @DeleteMapping("/{id}")
    public void deletePlace(@PathVariable Long id) {
        repo.deleteById(id);
    }
}