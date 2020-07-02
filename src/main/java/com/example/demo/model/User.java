package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotEmpty(message = "Please enter a name")
    private String name;
    @NotNull
    private int age;
    @Version
    private long version;

    @PostRemove
    public void postRemove() {
        System.out.println("Was removed");
    }
}
