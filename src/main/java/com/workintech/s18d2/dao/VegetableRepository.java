package com.workintech.s18d2.dao;
import com.workintech.s18d2.entity.Fruit;
import com.workintech.s18d2.entity.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VegetableRepository extends JpaRepository<Vegetable, Long>{
    List<Vegetable> findAllByOrderByPriceDesc();

    List<Vegetable> findAllByOrderByPriceAsc();

    List<Vegetable> findByNameContainingIgnoreCase(String name);
}
