package com.ga.springboothw.repository;

import com.ga.springboothw.model.Category;
import com.ga.springboothw.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String itemName);
    List<Item> findByCategoryId(Long categoryId);
}
