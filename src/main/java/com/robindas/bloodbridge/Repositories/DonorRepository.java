package com.robindas.bloodbridge.Repositories;

import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer> {
    Donor getDonorByUsers(Users users);
}
