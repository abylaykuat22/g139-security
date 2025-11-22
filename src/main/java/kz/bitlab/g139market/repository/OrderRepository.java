package kz.bitlab.g139market.repository;

import kz.bitlab.g139market.entity.Order;
import kz.bitlab.g139market.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
