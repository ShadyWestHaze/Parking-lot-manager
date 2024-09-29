package io.example.parkinglotmanagement;

import io.example.parkinglotmanagement.controller.ParkingSpotController;
import io.example.parkinglotmanagement.model.ParkingSpot;
import io.example.parkinglotmanagement.service.ParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParkingSpotControllerTest {

    private ParkingSpotController parkingSpotController;
    private ParkingSpotService parkingSpotService;

    @BeforeEach
    void setUp() {
        parkingSpotService = Mockito.mock(ParkingSpotService.class);
        parkingSpotController = new ParkingSpotController(parkingSpotService);
    }

    @Test
    void testCreateParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setName("A1");
        when(parkingSpotService.createParkingSpot(any(String.class))).thenReturn(Mono.just(parkingSpot));
        ResponseEntity<ParkingSpot> response = parkingSpotController.createParkingSpot(parkingSpot).block();
        assert response != null;
        assertEquals(201, response.getStatusCode().value());
        assertEquals("A1", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void testCreateParkingSpotError() {
        when(parkingSpotService.createParkingSpot(any(String.class)))
                .thenReturn(Mono.error(new RuntimeException("Service error")));
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setName("A1");
        ResponseEntity<ParkingSpot> response = parkingSpotController.createParkingSpot(parkingSpot).block();
        assert response != null;
        assertEquals(400, response.getStatusCode().value());
    }

}
