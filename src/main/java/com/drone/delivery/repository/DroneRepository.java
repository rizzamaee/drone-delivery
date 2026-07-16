package com.drone.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drone.delivery.entity.Drone;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
