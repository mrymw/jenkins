package com.ga.springboothw.controller;

import com.ga.springboothw.model.Item;
import com.ga.springboothw.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class ItemController {
    private ItemService itemService;

    @Autowired // creates class on its own
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/categories/{categoryId}/item")
    public Item createItem(@PathVariable(value = "categoryId") Long categoryId,
                           @RequestBody Item itemObject) {
        return itemService.createItem(categoryId, itemObject);
    }

    @GetMapping("/categories/{categoryId}/item")
    public List<Item> getItem(@PathVariable(value = "categoryId") Long categoryId) {
        return itemService.getItem(categoryId);
    }

    @GetMapping("/categories/{categoryId}/item/{itemId}")
    public Item getItem(@PathVariable(value = "categoryId") Long categoryId,
                            @PathVariable(value = "itemId") Long itemId) {
        return itemService.getItem(categoryId, itemId);
    }

    @DeleteMapping("/categories/{categoryId}/item/delete/{itemId}")
    public Optional<Item> deleteItem(@PathVariable(value = "categoryId") Long categoryId,
                                         @PathVariable(value = "itemId") Long itemId) {
        return itemService.deleteItem(categoryId, itemId);
    }

    @PutMapping("/categories/{categoryId}/item/update/{itemId}")
    public Optional<Item> updateItem(@PathVariable(value = "categoryId") Long categoryId,
                                     @PathVariable(value = "itemId") Long itemId, @RequestBody Item updateItem) {
        return itemService.updateItem(categoryId, itemId, updateItem);
    }

}
