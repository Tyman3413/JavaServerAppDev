package com.example.lab5;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String mascot;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamId")
    private Set<Player> players = new HashSet<>();

    // Конструктор по умолчанию
    public Team() {
        // *
    }

    // Конструктор с параметрами
    public Team(String name, String location, String mascot, Set<Player> players) {
        this.name = name;
        this.location = location;
        this.mascot = mascot;
        this.players = players;
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

    // Геттеры и сеттеры для location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Геттеры и сеттеры для mascot
    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    // Геттеры и сеттеры для players
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
