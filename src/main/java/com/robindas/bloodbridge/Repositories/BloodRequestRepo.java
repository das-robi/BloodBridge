package com.robindas.bloodbridge.Repositories;

import com.robindas.bloodbridge.Model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface BloodRequestRepo extends JpaRepository<BloodRequest, Integer> {
}
