package org.example.lab2_sysc4806;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    @Test
    void testAddBuddy() {
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(new BuddyInfo("John", "123-456-7890","505 Cygnus st"));
        assertEquals(1, addressBook.getBuddies().size());
    }
    @Test
    void testEmptyAddressBook() {
        AddressBook addressBook = new AddressBook();
        assertTrue(addressBook.getBuddies().isEmpty());
    }
}