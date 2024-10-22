package com.ga.springboothw.service;

import com.ga.springboothw.exception.InformationNotFoundException;
import com.ga.springboothw.model.Category;
import com.ga.springboothw.model.Item;
import com.ga.springboothw.model.User;
import com.ga.springboothw.repository.CategoryRepository;
import com.ga.springboothw.repository.ItemRepository;
import com.ga.springboothw.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class ItemService {
   private CategoryRepository categoryRepository;
   private ItemRepository itemRepository;
   @Autowired
   public void setCategoryRepository(CategoryRepository categoryRepository) {
       this.categoryRepository = categoryRepository;
   }
   @Autowired
   public void setItemRepository (ItemRepository itemRepository) {
       this.itemRepository = itemRepository;
   }
   public static User getCurrentLoggedUser(){
       MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return userDetails.getUser();
   }

   public Item createItem(Long categoryId, Item itemObject) {
       try {
           Optional<Category> category = categoryRepository.findById(categoryId);
           if (category.isPresent()) {
               itemObject.setCategory(category.get());
               itemObject.setUser(getCurrentLoggedUser());
               return itemRepository.save(itemObject);
           } else {
               throw  new InformationNotFoundException("NO SUCH CATEGORY!!");
           }
       } catch (NoSuchElementException e) {
           throw new InformationNotFoundException("NO SUCH ID");
       }
   }
    public List<Item> getItem(Long categoryID) {
        Optional<Category> category = categoryRepository.findById(categoryID);
        if(category.isPresent()) {
            return category.get().getItemList();
        } else {
            throw  new InformationNotFoundException("DOES NOT EXIST!!");
        }
    }
    public Item getItem(Long categoryID, Long itemID) {
        Optional<Category> category= categoryRepository.findById(categoryID);
        if(category.isPresent()) {

            Optional<Item> item = itemRepository.findByCategoryId(categoryID).stream().filter(i -> i.getId().equals(itemID)).findFirst();
            if (item.isEmpty()) {
                throw new InformationNotFoundException("iTEM ID NOT FOUND");
            } else {
                return item.get();
            }
        } else {
            throw new InformationNotFoundException("CATEGORY ID DOES NOT EXIST");
        }
    }
    public Optional<Item> deleteItem(Long categoryID, Long itemID) {
        Optional<Category> category = categoryRepository.findById(categoryID);
        if (category.isPresent()) {
            Optional<Item> item = itemRepository.findByCategoryId(categoryID).stream().filter(i-> i.getId().equals(itemID)).findFirst();
            if (item.isPresent()) {
                itemRepository.deleteById(itemID);
                return item;
            } else {
                throw new InformationNotFoundException("ITEM DOES NOT EXIST");
            }
        } else {
            throw new InformationNotFoundException("Category with ID: " + categoryID + "not found");
        }
    }
    public Optional<Item> updateItem (Long categoryID, Long itemID, Item updateItem) {
        Optional<Category> category = categoryRepository.findById(categoryID);
        if (category.isPresent()) {
            Optional<Item> item = itemRepository.findByCategoryId(categoryID).stream().filter(i-> i.getId().equals(itemID)).findFirst();
            if (item.isPresent()) {
                Item update = item.get();
                update.setName(updateItem.getName());
                update.setDescription(updateItem.getDescription());
                update.setDueDate(updateItem.getDueDate());
                return Optional.of(itemRepository.save(update));
            } else {
                throw new InformationNotFoundException("Item NOT FOUND!!");
            }
        } else {
            throw new InformationNotFoundException("Category with ID: " + categoryID + "not found");
        }
    }

}
