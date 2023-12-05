package com.nhnacademy.shoppingmall.address.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Address implements Serializable {
    private String address_id;
    private String address_line1;
    private String address_line2;
    private String city;
    private String sido;
    private String postal_code;

    private Timestamp modified_date;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(address_id, address.address_id) && Objects.equals(address_line1, address.address_line1)
                && Objects.equals(address_line2, address.address_line2) && Objects.equals(city,
                address.city) && Objects.equals(sido, address.sido) && Objects.equals(postal_code,
                address.postal_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address_id, address_line1, address_line2, city, sido, postal_code, modified_date);
    }

    public Address(String address_id, String address_line1, String address_line2, String city, String sido,
                   String postal_code) {
        this.address_id = address_id;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.city = city;
        this.sido = sido;
        this.postal_code = postal_code;
        this.modified_date = Timestamp.valueOf(LocalDateTime.now());
    }


    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setModified_date(Timestamp modified_date) {
        this.modified_date = modified_date;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public String getCity() {
        return city;
    }

    public String getSido() {
        return sido;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public Timestamp getModified_date() {
        return modified_date;
    }
}
