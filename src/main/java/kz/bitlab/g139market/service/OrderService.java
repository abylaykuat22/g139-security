package kz.bitlab.g139market.service;

import kz.bitlab.g139market.entity.Order;
import kz.bitlab.g139market.entity.Product;
import kz.bitlab.g139market.exception.NotExistsOnStockException;
import kz.bitlab.g139market.repository.OrderRepository;
import kz.bitlab.g139market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order create(Order order) {
        log.info("STARTED creation order: {}", order.getAuthor().getId());
        double totalPrice = 0;
        for (Product product : order.getProducts()) {
            Product entity = productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Product not found with id: " + product.getId()));
            totalPrice += entity.getPrice();
        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        boolean existsOnStock = true;
        if (!existsOnStock) {
            throw new NotExistsOnStockException("На складе недостаточно товаров для выполнения заказа");
        }
        // TODO: обновить количество товаров на складе
        // TODO: отправить уведомление пользователю и продавцу
        log.info("DONE creation order: {}", order.getAuthor().getId());
        return order;
    }
}
