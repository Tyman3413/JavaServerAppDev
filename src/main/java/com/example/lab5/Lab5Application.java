package com.example.lab5;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Lab5Application {
	@Autowired
	private TeamRepository teamRepository;

	public static void main(String[] args) {
		SpringApplication.run(Lab5Application.class, args);
	}

	@PostConstruct
	public void init() {
		List<Team> list = new ArrayList<>();

		Set<Player> set = new HashSet<>();
		set.add(new Player("Anton Shunin", "GK"));
		set.add(new Player("Eli Dasa", "Defender"));
		set.add(new Player("Vyacheslav Grulyov", "FW"));

		list.add(new Team("Dinamo", "Moscow", "Gladiator", set));
		list.add(new Team("CSKA", "Moscow", "Horse", null));

		teamRepository.saveAll(list);
	}

}
