package io.example.parkinglotmanagement;

import io.example.parkinglotmanagement.model.ParkingSpot;
import io.example.parkinglotmanagement.resolver.ParkingSpotQuery;
import io.example.parkinglotmanagement.service.ParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ParkingSpotQueryTest {

    @Mock
    private ParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingSpotQuery parkingSpotQuery;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParkingSpots() {
        ParkingSpot spot1 = new ParkingSpot("A1");
        ParkingSpot spot2 = new ParkingSpot("A2");
        when(parkingSpotService.getAllParkingSpots()).thenReturn(Flux.just(spot1, spot2));
        Flux<ParkingSpot> result = parkingSpotQuery.parkingSpots();
        assertNotNull(result);
        assertEquals(2, result.count().block());
    }

    @Test
    void testParkingSpot() {
        ParkingSpot spot = new ParkingSpot("A1");
        spot.setReserved(false);
        when(parkingSpotService.checkParkingSpotStatus(1L)).thenReturn(Mono.just(spot));
        Mono<ParkingSpot> result = parkingSpotQuery.parkingSpot("1");
        assertNotNull(result);
        assertEquals("A1", Objects.requireNonNull(result.block()).getName());
        assertFalse(Objects.requireNonNull(result.block()).isReserved());
    }
}