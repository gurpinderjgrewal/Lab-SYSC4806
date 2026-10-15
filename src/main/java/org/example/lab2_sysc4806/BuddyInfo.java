package org.example.lab2_sysc4806;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class BuddyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    @Column
    private String phoneNumber;
    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "fk_addressbook_id")
    @JsonBackReference
    private AddressBook addressBook;

    // Default no-arg constructor
    public BuddyInfo() {
    }
    public BuddyInfo(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }
    public AddressBook getAddressBook() {
        return addressBook;
    }
    @Override
    public String toString() {
        return String.format("BuddyInfo(id=%d, name=%s, phoneNumber=%s)", id, name, phoneNumber);
    }
}
