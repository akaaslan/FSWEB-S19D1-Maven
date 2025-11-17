package com.workintech.s18d2.services;

import com.workintech.s18d2.entity.Vegetable;
import java.util.List;

public interface VegetableService {

    List<Vegetable> getAllAsc();
    List<Vegetable> getAllDesc();
    Vegetable getById(Long id);
    Vegetable saveOrUpdate(Vegetable vegetable);
    void deleteById(Long id);
    List<Vegetable> findByNameContaining(String name);

}
