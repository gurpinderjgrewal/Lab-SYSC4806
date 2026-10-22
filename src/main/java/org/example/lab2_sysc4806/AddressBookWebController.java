package org.example.lab2_sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddressBookWebController {

    @Autowired
    private AddressBookRepo addressBookRepo;

    @GetMapping("/")
    public String index(Model model) {
        List<AddressBook> books = (List<AddressBook>) addressBookRepo.findAll();
        model.addAttribute("addressBooks", books);
        return "index";
    }

    @PostMapping("/addressbook/create")
    public String createAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBookRepo.save(addressBook);
        return "redirect:/";
    }

    @GetMapping("/addressbook/{id}")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook addressBook = addressBookRepo.findAddressBookById(id);
        if (addressBook == null) {
            return "error";
        }
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("buddies", addressBook.getBuddies());
        model.addAttribute("newBuddy", new BuddyInfo());
        return "addressbook";
    }

    @PostMapping("/addressbook/{addressBookId}/buddies/create")
    @Transactional
    public String addBuddy(@PathVariable Long addressBookId, @ModelAttribute("newBuddy") BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepo.findAddressBookById(addressBookId);
        if (addressBook == null) {
            return "error";
        }

        buddyInfo.setId(null);
        addressBook.addBuddy(buddyInfo);
        addressBookRepo.save(addressBook);

        return "redirect:/addressbook/" + addressBookId;
    }
}