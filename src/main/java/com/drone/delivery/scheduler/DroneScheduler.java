package com.drone.delivery.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.drone.delivery.entity.Drone;
import com.drone.delivery.enums.DroneState;
import com.drone.delivery.repository.DroneRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DroneScheduler {

    private final DroneRepository droneRepository;

    @Scheduled(fixedRate = 10000)
    public void updateDroneStatus() {

        for (Drone drone : droneRepository.findAll()) {

            switch (drone.getState()) {

                case LOADED:
                    drone.setState(DroneState.DELIVERING);
                    break;

                case DELIVERING:
                    drone.setState(DroneState.DELIVERED);

                    // Reduce battery by 10%
                    drone.setBatteryCapacity(drone.getBatteryCapacity() - 10);
                    break;

                case DELIVERED:
                    drone.setState(DroneState.RETURNING);
                    break;

                case RETURNING:
                    drone.setState(DroneState.IDLE);
                    break;

                default:
                    continue;
            }

            droneRepository.save(drone);

            System.out.println(
                    "Drone " + drone.getId() + " -> " + drone.getState());
        }
    }
}