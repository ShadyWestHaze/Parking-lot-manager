package io.example.parkinglotmanagement.controller;

import io.example.parkinglotmanagement.model.ParkingSpot;
import io.example.parkinglotmanagement.service.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/parking-spots")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public Mono<ResponseEntity<ParkingSpot>> createParkingSpot(@RequestBody ParkingSpot parkingSpot) {
        return parkingSpotService.createParkingSpot(parkingSpot.getName())
                .map(spot -> {
                    ResponseEntity<ParkingSpot> response = ResponseEntity.status(HttpStatus.CREATED).body(spot);
                    return response;
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping
    public Flux<ParkingSpot> getAllParkingSpots() {
        return parkingSpotService.getAllParkingSpots();
    }

    @PostMapping("/{id}/reserve")
    public Mono<ResponseEntity<ParkingSpot>> reserveParkingSpot(@PathVariable Long id) {
        return parkingSpotService.reserveParkingSpot(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(404).build())
                .onErrorReturn(ResponseEntity.status(409).build());
    }

    @GetMapping("/{id}/status")
    public Mono<ResponseEntity<ParkingSpot>> checkParkingSpotStatus(@PathVariable Long id) {
        return parkingSpotService.checkParkingSpotStatus(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(404).build());
    }

}
