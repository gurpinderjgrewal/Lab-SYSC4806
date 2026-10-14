package org.example.lab2_sysc4806;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuddyInfoTest {
    @Test
    void testBuddyName() {
        BuddyInfo buddyInfo = new BuddyInfo("John", "123-456-7890");
        assertEquals("John", buddyInfo.getName());
    }
    @Test
    void testBuddyPhoneNumber() {
        BuddyInfo buddyInfo = new BuddyInfo("John", "123-456-7890");
        assertEquals("123-456-7890", buddyInfo.getPhoneNumber());
    }
}