package com.workintech.s18d2.services;

import com.workintech.s18d2.dao.VegetableRepository;
import com.workintech.s18d2.entity.Vegetable;
import com.workintech.s18d2.exceptions.PlantException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VegetableServiceImpl implements VegetableService {

    private final VegetableRepository vegetableRepository;


    @Override
    public List<Vegetable> getAllAsc() {
        return vegetableRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Vegetable> getAllDesc() {
        return vegetableRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public Vegetable getById(Long id) {
        return vegetableRepository.findById(id).orElseThrow(() -> new PlantException("Vegetable not found with id: " + id));
    }

    @Override
    public Vegetable saveOrUpdate(Vegetable vegetable) {
        return vegetableRepository.save(vegetable);
    }

    @Override
    public void deleteById(Long id) {
        if (!vegetableRepository.existsById(id)) {
            throw new PlantException("Vegetable not found with id: " + id);
        }
        vegetableRepository.deleteById(id);
    }

    @Override
    public List<Vegetable> findByNameContaining(String name) {
        return vegetableRepository.findByNameContainingIgnoreCase(name);
    }
}
