package com.example.swimming.controller;

import com.example.swimming.dto.Client;
import com.example.swimming.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author BespoyasovaV
 */
@Controller
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/api/v0/pool/client/add")
    public String add(Model model) {
        return "add";
    }

    @PostMapping("/api/v0/pool/client/add")
    public String addClient(@RequestParam String name, @RequestParam String tel,
                            @RequestParam String email, Model model) {
         Client client=new Client(name, tel, email);
       clientRepository.save(client);
        return "redirect:/api/v0/pool/client/add";
    }

    @GetMapping("/api/v0/pool/client/all")
    public String blogMain(Model model) {
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "allClients";
    }

    @GetMapping("/api/v0/pool/client/{id}/update")
    public String update(@PathVariable(value = "id") long id, Model model) {
        Optional<Client> client = clientRepository.findById(id);
        ArrayList<Client> res = new ArrayList<>();
        client.ifPresent(res::add);
        model.addAttribute("client", res);
        return "update";
    }

    @PostMapping("/api/v0/pool/client/{clientId}/update")
    public String update(@PathVariable(value = "clientId") long clientId, @RequestParam String name, @RequestParam String tel,
                         @RequestParam String email, Model model) {
        Client client=clientRepository.findById(clientId).get();
        client.setName(name);
        client.setTel(tel);
       client.setEmail(email);
       clientRepository.save(client);
        return "redirect:/api/v0/pool/client/all";
    }
}
