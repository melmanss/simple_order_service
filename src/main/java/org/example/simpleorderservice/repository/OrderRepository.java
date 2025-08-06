package org.example.simpleorderservice.repository;

import jakarta.annotation.PostConstruct;
import org.example.simpleorderservice.model.Order;
import org.example.simpleorderservice.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong orderIdCounter = new AtomicLong();

    @PostConstruct
    public void init() {

        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Ноутбук");
        product1.setCost(new BigDecimal("1500.00"));
        products.add(product1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Миша");
        product2.setCost(new BigDecimal("25.50"));
        products.add(product2);

        Order initialOrder = new Order();
        initialOrder.setProducts(products);

        save(initialOrder);
    }

    public Optional<Order> findById(long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public Collection<Order> findAll() {
        return orders.values();
    }

    public Order save(Order order) {

        long newOrderId = orderIdCounter.incrementAndGet();
        order.setId(newOrderId);

        order.setCreationDate(LocalDate.now());

        BigDecimal totalCost = order.getProducts().stream()
                .map(Product::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalCost(totalCost);

        orders.put(newOrderId, order);
        return order;
    }
}