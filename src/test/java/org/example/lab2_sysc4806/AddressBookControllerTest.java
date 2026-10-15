package org.example.lab2_sysc4806;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressBookController.class)
public class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressBookRepo addressBookRepo;

    @MockBean
    private BuddyInfoRepo buddyInfoRepo;

    private AddressBook addressBook;
    private BuddyInfo buddyInfo;

    @BeforeEach
    void setUp() {
        addressBook = new AddressBook();
        addressBook.setId(1L);

        buddyInfo = new BuddyInfo();
        buddyInfo.setId(10L);
        // Assuming common properties for BuddyInfo (e.g., name, phone):
        // buddyInfo.setName("John Doe");
        // buddyInfo.setPhoneNumber("613-123-4567");
    }

    // -------------------------------------------------------------
    // AddressBook Endpoints
    // -------------------------------------------------------------

    @Test
    void testCreateAddressBook() throws Exception {
        when(addressBookRepo.save(any(AddressBook.class))).thenReturn(addressBook);

        mockMvc.perform(post("/addressbooks"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void testGetAllAddressBook() throws Exception {
        when(addressBookRepo.findAll()).thenReturn(Collections.singletonList(addressBook));

        mockMvc.perform(get("/addressbooks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    // -------------------------------------------------------------
    // BuddyInfo Endpoints
    // -------------------------------------------------------------

    @Test
    void testCreateBuddyInfo() throws Exception {
        when(buddyInfoRepo.save(any(BuddyInfo.class))).thenReturn(buddyInfo);

        mockMvc.perform(post("/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buddyInfo)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(10)));
    }

    @Test
    void testGetAllBuddyInfo() throws Exception {
        when(buddyInfoRepo.findAll()).thenReturn(Arrays.asList(buddyInfo));

        mockMvc.perform(get("/buddies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(10)));
    }

    @Test
    void testGetBuddyInfoById_Success() throws Exception {
        when(buddyInfoRepo.findById(10L)).thenReturn(Optional.of(buddyInfo));

        mockMvc.perform(get("/buddies/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(10)));
    }

    // -------------------------------------------------------------
    // Nested Relationship Endpoints
    // -------------------------------------------------------------

    @Test
    void testAddBuddyToAddressBook_Success() throws Exception {
        when(addressBookRepo.findAddressBookById(1L)).thenReturn(addressBook);
        when(buddyInfoRepo.findBuddyInfoById(10L)).thenReturn(buddyInfo);
        when(addressBookRepo.save(any(AddressBook.class))).thenReturn(addressBook);

        mockMvc.perform(post("/addressbooks/1/buddies/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.buddies", hasSize(1)))
                .andExpect(jsonPath("$.buddies[0].id", is(10)));
    }

    @Test
    void testAddBuddyToAddressBook_NotFound() throws Exception {
        when(addressBookRepo.findAddressBookById(1L)).thenReturn(null);
        when(buddyInfoRepo.findBuddyInfoById(10L)).thenReturn(buddyInfo);

        mockMvc.perform(post("/addressbooks/1/buddies/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBuddiesForAddressBook_Success() throws Exception {
        addressBook.addBuddy(buddyInfo);
        when(addressBookRepo.findAddressBookById(1L)).thenReturn(addressBook);

        mockMvc.perform(get("/addressbooks/1/buddies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(10)));
    }

    @Test
    void testGetBuddiesForAddressBook_NotFound() throws Exception {
        when(addressBookRepo.findAddressBookById(99L)).thenReturn(null);

        mockMvc.perform(get("/addressbooks/99/buddies"))
                .andExpect(status().isNotFound());
    }
}