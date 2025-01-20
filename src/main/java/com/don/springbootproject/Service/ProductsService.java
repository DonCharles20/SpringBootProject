package com.don.springbootproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.don.springbootproject.Model.Product;
import com.don.springbootproject.Repository.ProductsRepository;

import java.io.IOException;
import java.util.List;


@Service
public class ProductsService {
    @Autowired
    ProductsRepository productsRepository;
    public Product getProductById(int id) {
        return productsRepository.findById(id).orElse(null);
    }
    
    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }



    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        byte[] imageBytes = imageFile.getBytes();
        System.out.println("Image data size: " + imageBytes.length);  // Log the size to verify it's a byte array
        product.setImagename(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageBytes);  // Set the actual image data as byte[]
        
        return productsRepository.save(product);
    }
    
    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImagename(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return productsRepository.save(product);
    }

    public void deleteProducts(int id) {
        productsRepository.deleteById(id);
       
    }
    public List<Product> searchProducts(String keyWord) {
        return productsRepository.searchProducts(keyWord);
    }

    
}
