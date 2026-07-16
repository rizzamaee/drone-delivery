package com.drone.delivery.config;

import com.drone.delivery.entity.Drone;
import com.drone.delivery.entity.Medication;
import com.drone.delivery.enums.DroneModel;
import com.drone.delivery.enums.DroneState;
import com.drone.delivery.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final DroneRepository droneRepository;

    @Override
public void run(String... args) {

    if (droneRepository.count() == 0) {

        Drone drone = new Drone();
        drone.setSerialNumber("DRONE001");
        drone.setModel(DroneModel.HEAVYWEIGHT);
        drone.setWeightLimit(1000);
        drone.setBatteryCapacity(100);
        drone.setState(DroneState.IDLE);

        Medication medication1 = new Medication();
        medication1.setName("PARACETAMOL");
        medication1.setWeight(200);
        medication1.setCode("MED_001");
        medication1.setImage("paracetamol.png");

        Medication medication2 = new Medication();
        medication2.setName("VITAMIN_C");
        medication2.setWeight(100);
        medication2.setCode("MED_002");
        medication2.setImage("vitamin_c.png");

        drone.getMedications().add(medication1);
        drone.getMedications().add(medication2);

        droneRepository.save(drone);
    }
}
}