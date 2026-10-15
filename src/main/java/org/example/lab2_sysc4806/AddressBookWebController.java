package org.example.lab2_sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AddressBookWebController {
    @Autowired
    private AddressBookRepo addressBookRepo;

    @GetMapping("/")
    public String home(Model model) {
        List<AddressBook> all = (List<AddressBook>) addressBookRepo.findAll();
        if (all.isEmpty()) {
            AddressBook defaultBook = new AddressBook();

            // Populate sample buddies
            BuddyInfo buddy1 = new BuddyInfo("Alice Smith", "613-555-0101", "1125 Colonel By Dr");
            BuddyInfo buddy2 = new BuddyInfo("Bob Jones", "613-555-0199", "123 Bank St");

            defaultBook.addBuddy(buddy1);
            defaultBook.addBuddy(buddy2);

            // CascadeType.ALL on AddressBook saves the buddies automatically
            AddressBook savedBook = addressBookRepo.save(defaultBook);
            return "redirect:/addressbooks/" + savedBook.getId() + "/view";
        }
        return "redirect:/addressbooks/" + all.get(0).getId() + "/view";
    }
    @GetMapping("/addressbooks/{id}/view")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook addressBook = addressBookRepo.findAddressBookById(id);
        if (addressBook == null) {
            return "error";
        }
        model.addAttribute("addressBook",addressBook);
        model.addAttribute("buddies",addressBook.getBuddies());
        return "addressbook";
    }
}
