package io.example.parkinglotmanagement.repository;

import io.example.parkinglotmanagement.model.ParkingSpot;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ParkingSpotRepository extends ReactiveCrudRepository<ParkingSpot, Long> {
    Mono<ParkingSpot> findByName(String name);
}