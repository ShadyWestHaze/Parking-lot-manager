package io.example.parkinglotmanagement.resolver;

import io.example.parkinglotmanagement.model.ParkingSpot;
import io.example.parkinglotmanagement.service.ParkingSpotService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ParkingSpotQuery {
    private final ParkingSpotService parkingSpotService;

    public ParkingSpotQuery(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @QueryMapping
    public Flux<ParkingSpot> parkingSpots() {
        return parkingSpotService.getAllParkingSpots();
    }

    @QueryMapping
    public Mono<ParkingSpot> parkingSpot(@Argument String id) {
        return parkingSpotService.checkParkingSpotStatus(Long.parseLong(id));
    }

}
