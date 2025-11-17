package com.workintech.s18d2.dao;

import com.workintech.s18d2.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FruitRepository extends JpaRepository<Fruit, Long>{

    List<Fruit> findAllByOrderByPriceDesc();

    List<Fruit> findAllByOrderByPriceAsc();

    List<Fruit> findByNameContainingIgnoreCase(String name);
}
