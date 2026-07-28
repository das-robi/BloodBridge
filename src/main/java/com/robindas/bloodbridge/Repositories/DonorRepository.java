package com.robindas.bloodbridge.Repositories;

import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer> , JpaSpecificationExecutor<Donor> {
    Donor findByUsers(Users users);


    //Search Blood Donor
    List<Donor> findByBldGroupAndDistrict(String bldGroup, String district);

//    List<Donor> findByBldGroupAndDistrictAndAvailable(String bldGrp, String district, String available);
}
