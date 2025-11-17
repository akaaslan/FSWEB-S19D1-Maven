package com.workintech.s18d2;

import com.workintech.s18d2.dao.FruitRepository;
import com.workintech.s18d2.entity.Fruit;
import com.workintech.s18d2.entity.FruitType;
import com.workintech.s18d2.entity.Vegetable;
import com.workintech.s18d2.exceptions.PlantException;
import com.workintech.s18d2.services.FruitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MainTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FruitRepository fruitRepository;

    @Mock
    private FruitRepository mockFruitRepository;

    private FruitServiceImpl fruitService;

    private Fruit sampleFruit;

    @BeforeEach
    void setup() {
        // Veritabanına örnek meyveler ekle
        Fruit apple = new Fruit();
        apple.setName("Apple");
        apple.setPrice(15.0);
        apple.setFruitType(FruitType.SWEET);
        entityManager.persist(apple);

        Fruit lemon = new Fruit();
        lemon.setName("Lemon");
        lemon.setPrice(25.0);
        lemon.setFruitType(FruitType.SOUR);
        entityManager.persist(lemon);

        entityManager.flush();

        // Mock testler için örnek fruit
        sampleFruit = new Fruit();
        sampleFruit.setId(1L);
        sampleFruit.setName("Apple");
        sampleFruit.setPrice(15.0);
        sampleFruit.setFruitType(FruitType.SWEET);

        fruitService = new FruitServiceImpl(mockFruitRepository);
    }

    @Test
    @DisplayName("Fruit entity properties are set correctly")
    void testFruitProperties() {
        Fruit fruit = new Fruit();
        fruit.setId(1L);
        fruit.setName("Apple");
        fruit.setPrice(15.0);
        fruit.setFruitType(FruitType.SWEET);

        assertEquals(1L, fruit.getId());
        assertEquals("Apple", fruit.getName());
        assertEquals(15.0, fruit.getPrice());
        assertEquals(FruitType.SWEET, fruit.getFruitType());
    }

    @Test
    @DisplayName("Vegetable entity properties are set correctly")
    void testVegetableProperties() {
        Vegetable veg = new Vegetable();
        veg.setId(2L);
        veg.setName("Carrot");
        veg.setPrice(20.0);
        veg.setGrownOnTree(false);

        assertEquals(2L, veg.getId());
        assertEquals("Carrot", veg.getName());
        assertEquals(20.0, veg.getPrice());
        assertFalse(veg.getGrownOnTree());

        veg.setGrownOnTree(true);
        assertTrue(veg.getGrownOnTree());
    }

    @Test
    @DisplayName("FruitRepository returns fruits in ascending price order")
    void testGetByPriceAsc() {
        List<Fruit> fruits = fruitRepository.findAllByOrderByPriceAsc();
        assertEquals(2, fruits.size());
        assertTrue(fruits.get(0).getPrice() <= fruits.get(1).getPrice());
    }

    @Test
    @DisplayName("FruitRepository returns fruits in descending price order")
    void testGetByPriceDesc() {
        List<Fruit> fruits = fruitRepository.findAllByOrderByPriceDesc();
        assertEquals(2, fruits.size());
        assertTrue(fruits.get(0).getPrice() >= fruits.get(1).getPrice());
    }

    @Test
    @DisplayName("FruitRepository search by name works")
    void testSearchByName() {
        List<Fruit> fruits = fruitRepository.findByNameContainingIgnoreCase("Apple");
        assertEquals(1, fruits.size());
        assertEquals("Apple", fruits.get(0).getName());
    }

    @Test
    @DisplayName("FruitService getById returns fruit if exists")
    void testGetByIdFound() {
        when(mockFruitRepository.findById(anyLong())).thenReturn(Optional.of(sampleFruit));

        Fruit result = fruitService.getById(1L);

        assertNotNull(result);
        assertEquals("Apple", result.getName());
    }

    @Test
    @DisplayName("FruitService getById throws PlantException if not found")
    void testGetByIdNotFound() {
        when(mockFruitRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PlantException.class, () -> fruitService.getById(1L));
    }

    @Test
    @DisplayName("FruitService save works")
    void testSaveFruit() {
        when(mockFruitRepository.save(any(Fruit.class))).thenReturn(sampleFruit);

        Fruit saved = fruitService.saveOrUpdate(new Fruit());
        assertNotNull(saved);
        assertEquals("Apple", saved.getName());
    }

    @Test
    @DisplayName("FruitService delete works")
    void testDeleteFruit() {
        when(mockFruitRepository.findById(anyLong())).thenReturn(Optional.of(sampleFruit));
        doNothing().when(mockFruitRepository).delete(any(Fruit.class));


        Fruit deleted = fruitService.deleteById(1L);
        assertNotNull(deleted);
        assertEquals("Apple", deleted.getName());
    }

    @Test
    @DisplayName("FruitService searchByName works")
    void testSearchByNameService() {
        when(mockFruitRepository.findByNameContainingIgnoreCase(any())).thenReturn(Arrays.asList(sampleFruit));

        List<Fruit> fruits = fruitService.findByNameContaining("Apple");
        assertFalse(fruits.isEmpty());
        assertEquals(1, fruits.size());
        assertEquals("Apple", fruits.get(0).getName());
    }
}
