package com.drone.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drone.delivery.dto.DroneRequest;
import com.drone.delivery.dto.MedicationRequest;
import com.drone.delivery.entity.Drone;
import com.drone.delivery.entity.Medication;
import com.drone.delivery.service.DroneService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/drones")
@RequiredArgsConstructor
public class DroneController {

    private final DroneService droneService;

    // • Registering a drone
    @PostMapping
    public ResponseEntity<Drone> registerDrone(@Valid @RequestBody DroneRequest request) {
        return ResponseEntity.ok(droneService.registerDrone(request));
    }

    // • Loading a drone with medication
    @PostMapping("/{id}/load")
    public ResponseEntity<Drone> loadMedication(
            @PathVariable Long id,
            @Valid @RequestBody MedicationRequest request) {

        return ResponseEntity.ok(droneService.loadMedication(id, request));
    }

    // • Checking loaded medications for a given drone
    @GetMapping("/{id}/medications")
    public ResponseEntity<List<Medication>> getLoadedMedications(
            @PathVariable Long id) {

        return ResponseEntity.ok(droneService.getLoadedMedications(id));
    }

    // • Check drone availability for loading
    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones() {

        return ResponseEntity.ok(droneService.getAvailableDrones());

    }

    // • Check drone information (Battery)
    @GetMapping("/{id}/battery")
    public ResponseEntity<Integer> getBatteryLevel(@PathVariable Long id) {

        return ResponseEntity.ok(droneService.getBatteryLevel(id));
    }

}
