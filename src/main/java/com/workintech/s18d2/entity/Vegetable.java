package com.workintech.s18d2.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "vegetables", schema = "fsweb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vegetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Price must be provided")
    @PositiveOrZero(message = "Price must be zero or positive")
    private Double price;

    @NotNull(message = "isGrownOnTree must be provided")
    private Boolean grownOnTree;
}
