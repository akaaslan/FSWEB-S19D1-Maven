package com.workintech.s18d2.controller;

import com.workintech.s18d2.entity.Fruit;
import com.workintech.s18d2.exceptions.ApiResponse;
import com.workintech.s18d2.exceptions.BadRequestException;
import com.workintech.s18d2.services.FruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fruit")
@RequiredArgsConstructor
public class FruitController {

    private final FruitService fruitService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Fruit>>> getAllAsc() {
        List<Fruit> list = fruitService.getAllAsc();
        return ResponseEntity.ok(new ApiResponse<>("Fruits retrieved (asc)", list));
    }

    @GetMapping("/desc")
    public ResponseEntity<ApiResponse<List<Fruit>>> getAllDesc() {
        List<Fruit> list = fruitService.getAllDesc();
        return ResponseEntity.ok(new ApiResponse<>("Fruits retrieved (desc)", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Fruit>> getById(@PathVariable("id") Long id) {
        if (id < 0) throw new BadRequestException("Id must be >= 0");
        Fruit f = fruitService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>("Fruit retrieved", f));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Fruit>> createOrUpdate(@Valid @RequestBody Fruit fruit) {
        Fruit saved = fruitService.saveOrUpdate(fruit);
        return ResponseEntity.ok(new ApiResponse<>("Fruit saved/updated", saved));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<Fruit>>> findByName(@PathVariable("name") String name) {
        List<Fruit> list = fruitService.findByNameContaining(name);
        return ResponseEntity.ok(new ApiResponse<>("Fruits matching name", list));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") Long id) {
        if (id < 0) throw new BadRequestException("Id must be >= 0");
        fruitService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>("Fruit deleted", null));
    }
}
