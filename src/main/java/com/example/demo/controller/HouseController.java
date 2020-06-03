package com.example.demo.controller;

import com.example.demo.model.House;
import com.example.demo.repository.HouseRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HouseController {

    HouseRepository houseRepository;

    HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public List<House> getAll() {
        return houseRepository.findAll();
    }
}
