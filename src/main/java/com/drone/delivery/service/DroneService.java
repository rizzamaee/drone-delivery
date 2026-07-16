package com.drone.delivery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drone.delivery.dto.DroneRequest;
import com.drone.delivery.dto.MedicationRequest;
import com.drone.delivery.entity.Drone;
import com.drone.delivery.entity.Medication;
import com.drone.delivery.enums.DroneModel;
import com.drone.delivery.enums.DroneState;
import com.drone.delivery.repository.DroneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DroneService {

    private final DroneRepository droneRepository;

    // • Registering a drone
    public Drone registerDrone(DroneRequest request) {

        Drone drone = new Drone();

        drone.setSerialNumber(request.getSerialNumber());
        drone.setModel(request.getModel());
        drone.setBatteryCapacity(request.getBatteryCapacity());

        drone.setWeightLimit(getWeightLimit(request.getModel()));

        drone.setState(DroneState.IDLE);

        return droneRepository.save(drone);
    }

    // helper for getting the weight limit
    private double getWeightLimit(DroneModel model) {

        switch (model) {

            case LIGHTWEIGHT:
                return 250;

            case MIDDLEWEIGHT:
                return 500;

            case CRUISERWEIGHT:
                return 750;

            case HEAVYWEIGHT:
                return 1000;

            default:
                throw new IllegalArgumentException("Invalid drone model.");
        }
    }

    // • Loading a drone with medication
    public Drone loadMedication(Long droneId, MedicationRequest request) {

        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new IllegalArgumentException("Drone not found."));

        // Check battery
        if (drone.getBatteryCapacity() < 25) {
            throw new IllegalArgumentException("Drone battery is below 25%.");
        }

        // Calculate current total weight
        double currentWeight = 0;

        for (Medication medication : drone.getMedications()) {
            currentWeight += medication.getWeight();
        }

        // Check weight limit
        if (currentWeight + request.getWeight() > drone.getWeightLimit()) {
            throw new IllegalArgumentException("Drone is overloaded.");
        }

        // Create medication
        Medication medication = new Medication();
        medication.setName(request.getName());
        medication.setWeight(request.getWeight());
        medication.setCode(request.getCode());
        medication.setImage(request.getImage());

        // Add medication to drone
        drone.getMedications().add(medication);

        // Update state
        drone.setState(DroneState.LOADED);

        return droneRepository.save(drone);
    }

    // • Checking loaded medications for a given drone
    public List<Medication> getLoadedMedications(Long droneId) {

        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new IllegalArgumentException("Drone not found."));

        return drone.getMedications();
    }

    // • Check drone availability for loading
    public List<Drone> getAvailableDrones() {

        List<Drone> availableDrones = new ArrayList<>();

        for (Drone drone : droneRepository.findAll()) {

            if (drone.getBatteryCapacity() >= 25 &&
                    drone.getState() == DroneState.IDLE) {

                availableDrones.add(drone);
            }
        }

        return availableDrones;
    }

    // • Check drone information (Battery)
    public int getBatteryLevel(Long droneId) {

        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new IllegalArgumentException("Drone not found."));

        return drone.getBatteryCapacity();
    }

}
