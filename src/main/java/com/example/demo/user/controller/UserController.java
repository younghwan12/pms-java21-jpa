package com.example.demo.user.controller;


import java.util.List;
import java.util.Optional;

import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllPersons() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getPersonById(@PathVariable Long id) {
        Optional<User> person = userRepository.findById(id);
        if (person.isPresent()) {
            return ResponseEntity.ok(person.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public User createPerson(@RequestBody User person) {
        return userRepository.save(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updatePerson(@PathVariable Long id, @RequestBody User personDetails) {
        Optional<User> person = userRepository.findById(id);
        if (person.isPresent()) {
            User updatedPerson = person.get();
            updatedPerson.setName(personDetails.getName());
            updatedPerson.setEmail(personDetails.getEmail());
            userRepository.save(updatedPerson);
            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        Optional<User> person = userRepository.findById(id);
        if (person.isPresent()) {
            userRepository.delete(person.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
