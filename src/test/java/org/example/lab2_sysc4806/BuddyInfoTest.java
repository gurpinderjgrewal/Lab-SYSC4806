package org.example.lab2_sysc4806;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuddyInfoTest {
    @Test
    void testBuddyName() {
        BuddyInfo buddyInfo = new BuddyInfo("John", "123-456-7890", "505 Cygnus st");
        assertEquals("John", buddyInfo.getName());
    }
    @Test
    void testBuddyPhoneNumber() {
        BuddyInfo buddyInfo = new BuddyInfo("John", "123-456-7890", "505 Cygnus st");
        assertEquals("123-456-7890", buddyInfo.getPhoneNumber());
    }
    @Test
    void testBuddyAddress() {
        BuddyInfo buddyInfo = new BuddyInfo("John", "123-456-7890", "505 Cygnus st");
        assertEquals("505 Cygnus st", buddyInfo.getAddress());
    }
}