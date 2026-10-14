package org.example.lab2_sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressBookController {

    @Autowired
    private AddressBookRepo addressBookRepo;

    @Autowired
    private BuddyInfoRepo buddyInfoRepo;

    @PostMapping("/addressbooks")
    public ResponseEntity<AddressBook> createAddressBook() {
        AddressBook addressBook = new AddressBook();
        AddressBook savedAddressBook = addressBookRepo.save(addressBook);
        return new ResponseEntity<>(savedAddressBook, HttpStatus.CREATED);
    }
    @GetMapping("/addressbooks")
    public ResponseEntity<List< AddressBook>> getAllAddressBook() {
        List<AddressBook> addressBooks = (List<AddressBook>) addressBookRepo.findAll();
        return ResponseEntity.ok(addressBooks);

    }

    @PostMapping("/buddies")
    public ResponseEntity<BuddyInfo> createBuddyInfo(@RequestBody BuddyInfo buddyInfo) {
        BuddyInfo savedBuddyInfo = buddyInfoRepo.save(buddyInfo);
        return new ResponseEntity<>(savedBuddyInfo, HttpStatus.CREATED);
    }
    @GetMapping("/buddies")
    public ResponseEntity<List< BuddyInfo>> getAllBuddyInfo() {
        List<BuddyInfo> buddyInfos = (List<BuddyInfo>) buddyInfoRepo.findAll();
        return ResponseEntity.ok(buddyInfos);
    }
    @GetMapping("/buddies/{buddyId}")
    public ResponseEntity<BuddyInfo> getBuddyInfoById(@PathVariable Long buddyId) {
        BuddyInfo buddyInfo = buddyInfoRepo.findById(buddyId).orElse(null);
        return ResponseEntity.ok(buddyInfo);
    }

    @PostMapping("/addressbooks/{addressBookId}/buddies/{buddyId}")
    public ResponseEntity<AddressBook> addBuddyToAddressBook(@PathVariable Long addressBookId, @PathVariable Long buddyId) {
        AddressBook addressBook = addressBookRepo.findAddressBookById(addressBookId);
        BuddyInfo buddyInfo = buddyInfoRepo.findBuddyInfoById(buddyId);
        if (addressBook == null || buddyInfo == null) {
            return ResponseEntity.notFound().build();
        }
        addressBook.addBuddy(buddyInfo);
        AddressBook saved = addressBookRepo.save(addressBook);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/addressbooks/{addressBookId}/buddies")
    public ResponseEntity<List<BuddyInfo>> getBuddiesForAddressBook(@PathVariable Long addressBookId) {
        AddressBook addressBook = addressBookRepo.findAddressBookById(addressBookId);
        if (addressBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addressBook.getBuddies());
    }
}
