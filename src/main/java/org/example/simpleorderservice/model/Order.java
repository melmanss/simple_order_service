package org.example.simpleorderservice.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class Order {
    private Long id;
    private LocalDate creationDate;
    private BigDecimal totalCost;
    private List<Product> products;
}