package org.example.lab2_sysc4806;

import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepo extends CrudRepository<AddressBook, Long> {
    AddressBook findAddressBookById(Long id);
}
