package com.drone.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drone.delivery.entity.Medication;

public interface MedicationRepository
        extends JpaRepository<Medication, Long> {
}
