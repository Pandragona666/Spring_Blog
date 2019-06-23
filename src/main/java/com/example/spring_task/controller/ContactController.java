package com.example.spring_task.controller;

import com.example.spring_task.model.Contact;
import com.example.spring_task.repository.ContactRepository;
import com.example.spring_task.service.AutoMailingService;
import com.example.spring_task.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ContactController {

    ContactRepository contactRepository;
    ContactService contactService;
    AutoMailingService autoMailingService;

    @Autowired
    public ContactController(ContactRepository contactRepository,
                             ContactService contactService,
                             AutoMailingService autoMailingService) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
        this.autoMailingService = autoMailingService;
    }

    @GetMapping("/contacts")
    public String contact(Model model, Authentication auth){
        Contact contact = new Contact();
        if (auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            contact.setEmail(userDetails.getUsername());
        }
        model.addAttribute("contact", contact);
        return "contact";
    }

    @PostMapping("/contact")
    public String contact(@ModelAttribute @Valid Contact contact, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "contact";
        }
        //zapis do db
        contactService.addContact(contact);
        //wysyłanie automailingu
        autoMailingService.sendMessage(contact.getEmail(),
                "Potwierdzenie wysłania wiadomości",
                "Dziękujemy za wysłanie wiadomści");
        return "redirect:/";
    }









}
