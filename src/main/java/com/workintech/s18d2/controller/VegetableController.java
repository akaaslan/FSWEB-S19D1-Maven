package com.workintech.s18d2.controller;

import com.workintech.s18d2.entity.Vegetable;
import com.workintech.s18d2.exceptions.ApiResponse;
import com.workintech.s18d2.exceptions.BadRequestException;
import com.workintech.s18d2.services.VegetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/workintech/vegetables")
@RequiredArgsConstructor
public class VegetableController {
    private final VegetableService vegetableService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Vegetable>>> getAllAsc() {
        List<Vegetable> list = vegetableService.getAllAsc();
        return ResponseEntity.ok(new ApiResponse<>("Vegetables retrieved (asc)", list));
    }

    @GetMapping("/desc")
    public ResponseEntity<ApiResponse<List<Vegetable>>> getAllDesc() {
        List<Vegetable> list = vegetableService.getAllDesc();
        return ResponseEntity.ok(new ApiResponse<>("Vegetables retrieved (desc)", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vegetable>> getById(@PathVariable("id") Long id) {
        if (id < 0) throw new BadRequestException("Id must be >= 0");
        Vegetable v = vegetableService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>("Vegetable retrieved", v));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Vegetable>> createOrUpdate(@Valid @RequestBody Vegetable vegetable) {
        Vegetable saved = vegetableService.saveOrUpdate(vegetable);
        return ResponseEntity.ok(new ApiResponse<>("Vegetable saved/updated", saved));
    }

    @PostMapping("/{name}")
    public ResponseEntity<ApiResponse<List<Vegetable>>> findByName(@PathVariable("name") String name) {
        List<Vegetable> list = vegetableService.findByNameContaining(name);
        return ResponseEntity.ok(new ApiResponse<>("Vegetables matching name", list));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") Long id) {
        if (id < 0) throw new BadRequestException("Id must be >= 0");
        vegetableService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>("Vegetable deleted", null));
    }
}
