package com.robindas.bloodbridge.DTO;

import java.time.LocalDate;

public class DonorResponse {

//    private Integer donId;
    private String donorName;
    private String bldGroup;
    private String city;
    private String district;
    private String phone;
    private LocalDate lastDonateDate;
    private boolean available;

    public DonorResponse(Integer donId, String bldGroup, String city, String phone, LocalDate lastDonateDate, String district, boolean available, String donorName) {
        this.bldGroup = bldGroup;
        this.city = city;
        this.phone = phone;
        this.lastDonateDate = lastDonateDate;
        this.available = available;
        this.district = district;
//        this.donId = donId;
        this.donorName = donorName;
    }

    public DonorResponse() {
    }

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

//    public Integer getDonId() {
//        return donId;
//    }
//
//    public void setDonId(Integer donId) {
//        this.donId = donId;
//    }


    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
}
