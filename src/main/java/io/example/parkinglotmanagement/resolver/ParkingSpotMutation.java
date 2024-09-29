package io.example.parkinglotmanagement.resolver;

import io.example.parkinglotmanagement.model.ParkingSpot;
import io.example.parkinglotmanagement.service.ParkingSpotService;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ParkingSpotMutation {
    private final ParkingSpotService parkingSpotService;

    public ParkingSpotMutation(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @MutationMapping
    public Mono<ParkingSpot> createParkingSpot(@Argument String name) {
        return parkingSpotService.createParkingSpot(name);
    }

    @MutationMapping
    public Mono<ParkingSpot> reserveParkingSpot(@Argument Long id) {
        return parkingSpotService.reserveParkingSpot(id);
    }
}
