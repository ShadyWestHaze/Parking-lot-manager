package io.example.parkinglotmanagement.service;

import io.example.parkinglotmanagement.model.ParkingSpot;
import io.example.parkinglotmanagement.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public Mono<ParkingSpot> createParkingSpot(String spotName) {
        if (spotName == null || spotName.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Parking spot name cannot be empty or null"));
        }
        ParkingSpot parkingSpot = new ParkingSpot(spotName);
        return parkingSpotRepository.save(parkingSpot);
    }


    public Flux<ParkingSpot> getAllParkingSpots() {
        return parkingSpotRepository.findAll();
    }

    public Mono<ParkingSpot> reserveParkingSpot(Long id) {
        return parkingSpotRepository.findById(id)
                .flatMap(parkingSpot -> {
                    if (parkingSpot.isReserved()) {
                        return Mono.error(new RuntimeException("Parking spot already reserved"));
                    }
                    parkingSpot.setReserved(true);
                    return parkingSpotRepository.save(parkingSpot);
                });
    }

    public Mono<ParkingSpot> checkParkingSpotStatus(Long id) {
        return parkingSpotRepository.findById(id);
    }
}


