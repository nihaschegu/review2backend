package com.traveleasy.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.traveleasy.backend.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}