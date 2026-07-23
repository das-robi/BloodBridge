package com.robindas.bloodbridge.Repositories;

import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.DonationResponse;
import com.robindas.bloodbridge.Model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonResRepository extends JpaRepository<DonationResponse, Integer> {

    boolean existsByBloodRequestAndDonor(BloodRequest bloodRequest, Donor donor);
}
