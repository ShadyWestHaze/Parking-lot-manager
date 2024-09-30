package io.example.parkinglotmanagement;

import io.example.parkinglotmanagement.model.ParkingSpot;
import io.example.parkinglotmanagement.repository.ParkingSpotRepository;
import io.example.parkinglotmanagement.service.ParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ParkingSpotServiceTest {

    private ParkingSpotService parkingSpotService;
    private ParkingSpotRepository parkingSpotRepository;

    @BeforeEach
    void setUp() {
        parkingSpotRepository = Mockito.mock(ParkingSpotRepository.class);
        parkingSpotService = new ParkingSpotService(parkingSpotRepository);
    }

    @Test
    void testCreateParkingSpotWithNullName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parkingSpotService.createParkingSpot(null).block());
        assertEquals("Parking spot name cannot be empty or null", exception.getMessage());
    }


    @Test
    void testCreateParkingSpotWithValidName() {
        ParkingSpot parkingSpot = new ParkingSpot("A1");
        when(parkingSpotRepository.save(any(ParkingSpot.class))).thenReturn(Mono.just(parkingSpot));
        Mono<ParkingSpot> result = parkingSpotService.createParkingSpot("A1");
        assertEquals("A1", Objects.requireNonNull(result.block()).getName());
    }

    @Test
    void testReserveParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot("A1");
        parkingSpot.setReserved(false);
        when(parkingSpotRepository.findById(1L)).thenReturn(Mono.just(parkingSpot));
        when(parkingSpotRepository.save(any(ParkingSpot.class))).thenReturn(Mono.just(parkingSpot));
        Mono<ParkingSpot> result = parkingSpotService.reserveParkingSpot(1L);
        assertTrue(Objects.requireNonNull(result.block()).isReserved());
    }

    @Test
    void testCreateParkingSpotDuplicate() {
        ParkingSpot existingSpot = new ParkingSpot();
        existingSpot.setName("A1");
        when(parkingSpotRepository.findByName(any(String.class))).thenReturn(Mono.just(existingSpot));
        assertThrows(RuntimeException.class, () -> parkingSpotService.createParkingSpot("A1").block());
    }

    @Test
    void testCheckParkingSpotStatusWithExistingId() {
        ParkingSpot parkingSpot = new ParkingSpot("A1");
        when(parkingSpotRepository.findById(1L)).thenReturn(Mono.just(parkingSpot));
        Mono<ParkingSpot> result = parkingSpotService.checkParkingSpotStatus(1L);
        assertNotNull(result.block());
        assertEquals("A1", Objects.requireNonNull(result.block()).getName());
    }

    @Test
    void testCheckParkingSpotStatusWithNonExistentId() {
        when(parkingSpotRepository.findById(2L)).thenReturn(Mono.empty());
        Mono<ParkingSpot> result = parkingSpotService.checkParkingSpotStatus(2L);
        assertNotEquals(Boolean.TRUE, result.hasElement().block());
    }

}
