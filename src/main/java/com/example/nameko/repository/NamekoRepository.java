package com.example.nameko.repository;

import com.example.nameko.dto.Nameko;
import com.example.nameko.mapper.NamekoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NamekoRepository {
    private final NamekoMapper namekoMapper;

    public NamekoRepository(NamekoMapper namekoMapper) {
        this.namekoMapper = namekoMapper;
    }

    public List<Nameko> findAll() {
        return namekoMapper.findAll();
    }

    public int insertNameko(Nameko nameko) {
        return namekoMapper.insert(nameko);
    }

    public Nameko findById(long id) {
        return namekoMapper.findById(id);
    }

    public boolean delete(long id) {
        return namekoMapper.delete(id) == 1;
    }

    public Nameko update(Nameko nameko) {
        if (namekoMapper.update(nameko) == 0) {
            throw new IllegalArgumentException("Nameko not found: " + nameko.getId());
        }

        return nameko;
    }
}