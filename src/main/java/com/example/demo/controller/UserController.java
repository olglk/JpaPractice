package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.exception.HouseNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("/house")
@Validated
public class HouseController {

    UserService userService;
    UserRepository userRepository;

    HouseController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findHouseById(@PathVariable int id) {
        return userRepository
                .findById(id).orElseThrow(()-> new HouseNotFoundException("House with current id not found: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable @Max(2) int id) {

        userService.deleteById(id);
    }

    @PostMapping
    public User postHouse(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/findallsql")
    public List<User> findAllSQL() {
        return userService.findAllSQL();
    }

    @GetMapping("/findalljpql")
    public List<User> findAllJPQL() {
        return userService.findAllJPQL();
    }

    @GetMapping("/criteria")
    public List<User> getCriteriaHouse() {
        return userService.getCriteriaHouse();
    }

    @GetMapping("/criteria/names")
    public List<String> getCriteriaHouseNames() {
        return userService.getCriteriaHouseNames();
    }

    @GetMapping("/criteria/sumages")
    public Integer getCriteriaHouseSumAges() {
        return userService.getCriteriaHouseSumAges();
    }

}
