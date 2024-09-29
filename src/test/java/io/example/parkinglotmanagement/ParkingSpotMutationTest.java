package io.example.parkinglotmanagement;

import io.example.parkinglotmanagement.model.ParkingSpot;
import io.example.parkinglotmanagement.resolver.ParkingSpotMutation;
import io.example.parkinglotmanagement.service.ParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ParkingSpotMutationTest {

    private ParkingSpotMutation parkingSpotMutation;
    private ParkingSpotService parkingSpotService;

    @BeforeEach
    void setUp() {
        parkingSpotService = Mockito.mock(ParkingSpotService.class);
        parkingSpotMutation = new ParkingSpotMutation(parkingSpotService);
    }

    @Test
    void testCreateParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot("A1");
        when(parkingSpotService.createParkingSpot(anyString())).thenReturn(Mono.just(parkingSpot));
        Mono<ParkingSpot> result = parkingSpotMutation.createParkingSpot("A1");
        assertNotNull(result.block());
        assertEquals("A1", Objects.requireNonNull(result.block()).getName());
    }

    @Test
    void testCreateParkingSpotError() {
        when(parkingSpotService.createParkingSpot(anyString())).thenReturn(Mono.error(new RuntimeException("Error creating parking spot")));
        Mono<ParkingSpot> result = parkingSpotMutation.createParkingSpot("A1");
        assertThrows(RuntimeException.class, result::block);
    }

    @Test
    void testReserveParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot("A1");
        parkingSpot.setReserved(false);
        when(parkingSpotService.reserveParkingSpot(1L)).thenAnswer(invocation -> {
            parkingSpot.setReserved(true);
            return Mono.just(parkingSpot);
        });
        Mono<ParkingSpot> result = parkingSpotMutation.reserveParkingSpot(1L);
        ParkingSpot reservedSpot = result.block();
        assertNotNull(reservedSpot);
        assertTrue(reservedSpot.isReserved(), "The parking spot should be marked as reserved.");
    }

    @Test
    void testReserveParkingSpotAlreadyReserved() {
        ParkingSpot parkingSpot = new ParkingSpot("A1");
        parkingSpot.setReserved(true);
        when(parkingSpotService.reserveParkingSpot(1L)).thenReturn(Mono.error(new RuntimeException("Parking spot already reserved")));
        Mono<ParkingSpot> result = parkingSpotMutation.reserveParkingSpot(1L);
        assertThrows(RuntimeException.class, result::block);
    }
}
