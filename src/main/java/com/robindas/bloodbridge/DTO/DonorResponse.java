package com.robindas.bloodbridge.DTO;

public class DonorResponse {

    private String bldGroup;
    private String city;
    private String phone;
    private boolean available;

    public DonorResponse(String bldGroup, String city, String phone, boolean available) {
        this.bldGroup = bldGroup;
        this.city = city;
        this.phone = phone;
        this.available = available;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "DonorResponse{" +
                "bldGroup='" + bldGroup + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", available=" + available +
                '}';
    }
}
