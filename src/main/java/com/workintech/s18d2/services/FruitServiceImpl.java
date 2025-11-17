package com.workintech.s18d2.services;

import com.workintech.s18d2.dao.FruitRepository;
import com.workintech.s18d2.entity.Fruit;
import com.workintech.s18d2.exceptions.PlantException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FruitServiceImpl implements FruitService{
    private final FruitRepository fruitRepository;
    @Override
    public List<Fruit> getAllAsc() {
        return fruitRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Fruit> getAllDesc() {
        return fruitRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public Fruit getById(Long id) {
        return fruitRepository.findById(id).orElseThrow(() -> new PlantException("Fruit not found with id: " + id));
    }

    @Override
    public Fruit saveOrUpdate(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    @Override
    public Fruit deleteById(Long id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new PlantException("Fruit not found with id: " + id));
        fruitRepository.delete(fruit);
        return fruit;
    }

    @Override
    public List<Fruit> findByNameContaining(String name) {
        return fruitRepository.findByNameContainingIgnoreCase(name);
    }
}
