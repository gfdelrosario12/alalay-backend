package com.alalay.backend.resolvers;

import com.alalay.backend.model.User;
import com.alalay.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @QueryMapping
    public List<User> users() { return userRepository.findAll(); }

    @QueryMapping
    public User user(@Argument UUID id) {
        return userRepository.findById(id).orElse(null); 
    }

    @MutationMapping
    public User createUser(@Argument String email,
                           @Argument String firstName,
                           @Argument String middleName,
                           @Argument String lastName,
                           @Argument String permanentAddress,
                           @Argument Integer age,
                           @Argument String birthDate,
                           @Argument String emergencyContact,
                           @Argument Role role) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setPermanentAddress(permanentAddress);
        user.setAge(age);
        if(birthDate!=null) user.setBirthDate(LocalDate.parse(birthDate));
        user.setEmergencyContact(emergencyContact);
        user.setRole(role);
        return userRepository.save(user);
    }
}
