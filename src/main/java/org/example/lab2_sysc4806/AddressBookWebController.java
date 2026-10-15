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
            addressBookRepo.save(defaultBook);
            return "redirect:/addressbooks/" + defaultBook.getId() + "/view";
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
