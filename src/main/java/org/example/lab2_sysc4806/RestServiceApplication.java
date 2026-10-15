package org.example.lab2_sysc4806;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class RestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(AddressBookRepo addressBookRepo) {
        return args -> {

            AddressBook defaultBook = new AddressBook();

            BuddyInfo buddy1 = new BuddyInfo("Alice Smith", "613-555-0101", "1125 Colonel By Dr");
            BuddyInfo buddy2 = new BuddyInfo("Bob Jones", "613-555-0199", "123 Bank St");

            defaultBook.addBuddy(buddy1);
            defaultBook.addBuddy(buddy2);

            // CascadeType.ALL persists both the AddressBook and its Buddies
            AddressBook savedBook = addressBookRepo.save(defaultBook);
        };
    }
}