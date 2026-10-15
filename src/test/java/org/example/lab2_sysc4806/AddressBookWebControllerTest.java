package org.example.lab2_sysc4806;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        mockMvc.perform(get("/addressbooks/1/view"))
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

        mockMvc.perform(get("/addressbooks/99/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeDoesNotExist("addressBook"));
    }
}