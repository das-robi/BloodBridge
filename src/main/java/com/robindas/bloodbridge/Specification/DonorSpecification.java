package com.robindas.bloodbridge.Specification;

import com.robindas.bloodbridge.Model.Donor;
import org.springframework.data.jpa.domain.Specification;

public class DonorSpecification {

    public static Specification<Donor> hasBloodGroup(String bldGrp){
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("bldGroup"), bldGrp);

    }

    public static Specification<Donor> hasDistrict(String district){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("district"), district);

    }

    public static Specification<Donor> hasCity(String city){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("city"), city);

    }

    public static Specification<Donor> hasAvailable(Boolean available){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("available"), available);
    }

}
