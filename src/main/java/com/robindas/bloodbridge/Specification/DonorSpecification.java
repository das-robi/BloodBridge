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

    public static Specification<Donor> hasCoordinates() {

        return (root, query, cb) -> cb.and(cb.isNotNull(root.get("latitude")), cb.isNotNull(root.get("longitude")));

    }

    /** PostGIS ST_DistanceSphere returns metres between two WGS84 points. */
    public static Specification<Donor> withinRadius(double latitude, double longitude, double radiusMeters) {

        return (root, query, cb) -> {

            var donorPoint = cb.function("ST_SetSRID", Object.class,
                    cb.function("ST_MakePoint", Object.class, root.get("longitude"), root.get("latitude")), cb.literal(4326));
            var searchPoint = cb.function("ST_SetSRID", Object.class,
                    cb.function("ST_MakePoint", Object.class, cb.literal(longitude), cb.literal(latitude)), cb.literal(4326));
            return cb.lessThanOrEqualTo(cb.function("ST_DistanceSphere", Double.class, donorPoint, searchPoint), radiusMeters);

        };

    }

}
