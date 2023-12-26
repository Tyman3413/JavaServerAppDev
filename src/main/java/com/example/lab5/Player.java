package com.example.lab5;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String position;

    // Конструктор по умолчанию
    public Player() {
        // *
    }

    // Конструктор с параметрами
    public Player(String name, String position) {
        this.name = name;
        this.position = position;
    }

    // Геттеры и сеттеры для id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Геттеры и сеттеры для name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Геттеры и сеттеры для position
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
