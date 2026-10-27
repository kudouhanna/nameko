package com.example.nameko.service;

import com.example.nameko.dto.Nameko;
import com.example.nameko.repository.NamekoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NamekoService {
    private final NamekoRepository namekoRepository;

    public NamekoService(NamekoRepository namekoRepository) {
        this.namekoRepository = namekoRepository;
    }

    public List<Nameko> findAll() {
        return namekoRepository.findAll();
    }

    public Nameko saveNameko(Nameko nameko) {

        if (nameko.getName() == null || nameko.getName().isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }

        if (nameko.getDescription() == null || nameko.getDescription().isEmpty()) {
            throw new IllegalArgumentException("description is empty");
        }

        namekoRepository.insertNameko(nameko);
        return nameko;
    }

    public Nameko findById(long id) {
        Nameko nameko = namekoRepository.findById(id);

        if (nameko == null) {
            throw new IllegalArgumentException("Nameko not found: " + id);
        }

        return nameko;
    }

    public boolean delete(long id) {
        if (!namekoRepository.delete(id)) {
            throw new IllegalArgumentException("Nameko not found: " + id);
        }
        return true;
    }

    @Transactional
    public Nameko update(Nameko nameko, long id) {

        if (nameko.getName() == null || nameko.getName().isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }

        if (nameko.getDescription() == null || nameko.getDescription().isEmpty()) {
            throw new IllegalArgumentException("description is empty");
        }

        Nameko targetNameko = namekoRepository.findById(id);
        if (targetNameko == null) {
            throw new IllegalArgumentException("Nameko not found: " + id);
        }

        nameko.setId(id);
        nameko.setCreatedAt(targetNameko.getCreatedAt());
        return namekoRepository.update(nameko);
    }
}