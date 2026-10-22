package org.example.lab2_sysc4806;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class AddressBook{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<BuddyInfo> buddies;

    public AddressBook() {
        buddies = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public void setBuddies(List<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    public void addBuddy(BuddyInfo buddyInfo) {
        buddies.add(buddyInfo);
        buddyInfo.setAddressBook(this);
    }
    public void removeBuddy(BuddyInfo buddyInfo) {
        buddies.remove(buddyInfo);
        buddyInfo.setAddressBook(null);
    }
    @Override
    public String toString() {
        return String.format("AddressBook(id=%d, buddies count=%d)", id, buddies.size());
    }
}