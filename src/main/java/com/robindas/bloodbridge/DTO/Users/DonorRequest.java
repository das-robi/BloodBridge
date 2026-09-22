package com.robindas.bloodbridge.DTO.Users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDate;

public class DonorRequest {

//    private String donorName;
    @NotBlank
    private String bldGroup;

    @NotBlank
    private String city;

    @NotBlank
    @Pattern(regexp = "^01[3-9]\\d{8}$")
    private String phone;

    @NotBlank
    private String district;

    @NotNull @DecimalMin("-90.0") @DecimalMax("90.0")
    private Double latitude;

    @NotNull @DecimalMin("-180.0") @DecimalMax("180.0")
    private Double longitude;

    @NotNull(message = "Last donation date is required")
    @Past(message = "Last donation date must be in the past")
    private LocalDate lastDonateDate;

    @NotNull(message = "Availability is required")
    private Boolean available;

    public DonorRequest(String bldGroup, String city, String phone, LocalDate lastDonateDate, String district, boolean available) {
        this.bldGroup = bldGroup;
        this.city = city;
        this.phone = phone;
        this.lastDonateDate = lastDonateDate;
        this.available = available;
        this.district = district;
//        this.donorName = donorName;
    }

    public DonorRequest() {
    }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getBldGroup() {
        return bldGroup;
    }

    public void setBldGroup(String bldGroup) {
        this.bldGroup = bldGroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getLastDonateDate() {
        return lastDonateDate;
    }

    public void setLastDonateDate(LocalDate lastDonateDate) {
        this.lastDonateDate = lastDonateDate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

//    public String getDonorName() {
//        return donorName;
//    }
//
//    public void setDonorName(String donorName) {
//        this.donorName = donorName;
//    }
}
