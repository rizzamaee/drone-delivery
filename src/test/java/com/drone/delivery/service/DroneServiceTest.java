package com.drone.delivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.drone.delivery.dto.DroneRequest;
import com.drone.delivery.entity.Drone;
import com.drone.delivery.enums.DroneModel;
import com.drone.delivery.enums.DroneState;
import com.drone.delivery.repository.DroneRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneService droneService;

    @Test
    void registerDrone_ShouldRegisterSuccessfully() {

        // Arrange
        DroneRequest request = new DroneRequest();
        request.setSerialNumber("DRONE001");
        request.setModel(DroneModel.HEAVYWEIGHT);
        request.setBatteryCapacity(100);

        Drone savedDrone = new Drone();
        savedDrone.setSerialNumber(request.getSerialNumber());
        savedDrone.setModel(request.getModel());
        savedDrone.setBatteryCapacity(request.getBatteryCapacity());
        savedDrone.setWeightLimit(1000);
        savedDrone.setState(DroneState.IDLE);

        when(droneRepository.save(org.mockito.ArgumentMatchers.any(Drone.class)))
                .thenReturn(savedDrone);

        // Act
        Drone result = droneService.registerDrone(request);

        // Assert
        assertNotNull(result);
        assertEquals("DRONE001", result.getSerialNumber());
        assertEquals(DroneModel.HEAVYWEIGHT, result.getModel());
        assertEquals(100, result.getBatteryCapacity());
        assertEquals(1000, result.getWeightLimit());
        assertEquals(DroneState.IDLE, result.getState());
    }
}