package org.example.productservice.services;

import org.example.productservice.exceptions.CategoryNotFoundException;
import org.example.productservice.exceptions.ProductNotFoundException;
import org.example.productservice.models.Category;
import org.example.productservice.models.Product;
import org.example.productservice.repositories.CategoryRepository;
import org.example.productservice.repositories.ProductRepository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class SelfProductService implements ProductService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    SelfProductService (ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Product getProductById(Long id) {
        Optional<Product>  optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException(id,"Product not found");
        }

        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        Category  category = product.getCategory();
        if(category.getId()==null){
            product.setCategory(categoryRepository.save(category));
        }
        Product product1 = productRepository.save(product);
        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());

        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException("Invalid category id passed");
        }

        product1.setCategory(optionalCategory.get());
        return product1;
    }
}