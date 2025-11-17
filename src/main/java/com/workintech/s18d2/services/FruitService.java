package com.workintech.s18d2.services;

import com.workintech.s18d2.entity.Fruit;
import java.util.List;

public interface FruitService {

    List<Fruit> getAllAsc();
    List<Fruit> getAllDesc();
    Fruit getById(Long id);
    Fruit saveOrUpdate(Fruit fruit);
    Fruit deleteById(Long id);
    List<Fruit> findByNameContaining(String name);

}
