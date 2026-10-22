package org.example.lab2_sysc4806;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.ArgumentCaptor;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressBookWebController.class)
public class AddressBookWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookRepo addressBookRepo;

    @Test
    void testViewAddressBook_Success() throws Exception {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(1L);

        BuddyInfo buddy = new BuddyInfo();
        buddy.setId(20L);
        addressBook.addBuddy(buddy);

        when(addressBookRepo.findAddressBookById(1L)).thenReturn(addressBook);

        mockMvc.perform(get("/addressbook/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("addressbook"))
                .andExpect(model().attributeExists("addressBook"))
                .andExpect(model().attributeExists("buddies"))
                .andExpect(model().attribute("addressBook", addressBook))
                .andExpect(model().attribute("buddies", addressBook.getBuddies()));
    }

    @Test
    void testViewAddressBook_NotFound_ReturnsErrorView() throws Exception {
        when(addressBookRepo.findAddressBookById(99L)).thenReturn(null);

        mockMvc.perform(get("/addressbook/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeDoesNotExist("addressBook"));
    }

    @Test
    void testAddBuddy_SavesNewBuddyWithoutAddressBookId() throws Exception {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(1L);
        when(addressBookRepo.findAddressBookById(1L)).thenReturn(addressBook);
        when(addressBookRepo.save(any(AddressBook.class))).thenReturn(addressBook);

        mockMvc.perform(post("/addressbook/1/buddies/create")
                        .param("name", "Jane Doe")
                        .param("phoneNumber", "613-555-0000")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addressbook/1"));

        ArgumentCaptor<AddressBook> captor = ArgumentCaptor.forClass(AddressBook.class);
        verify(addressBookRepo).save(captor.capture());
        BuddyInfo savedBuddy = captor.getValue().getBuddies().get(0);
        assertNull(savedBuddy.getId());
        assertEquals("Jane Doe", savedBuddy.getName());
        assertEquals("613-555-0000", savedBuddy.getPhoneNumber());
        assertEquals("123 Main St", savedBuddy.getAddress());
    }
}