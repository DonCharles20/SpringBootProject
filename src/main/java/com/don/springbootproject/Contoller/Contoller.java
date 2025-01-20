package com.don.springbootproject.Contoller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.don.springbootproject.Service.ProductsService;

import java.util.List;

import com.don.springbootproject.Model.Product; // Add this import statement

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Contoller {

    @Autowired
    ProductsService service;
    

    @GetMapping("/products")
    public ResponseEntity<List<Product>> Products() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK); 
        
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        System.out.println("Here");

        try{
            Product product1 = service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        
            }catch(IOException e){
              return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        
            }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]>getImageByProductId(@PathVariable int productId){
        Product product=service.getProductById(productId);
        //Long imageFile = product.getImageData();
        byte[] imageFile= product.getImageData();
    
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf(product.getImageType()))
            .body(imageFile);
  }



    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product product, @RequestPart MultipartFile imageFile) {

        Product product1;//Create a product object for the Try catch block
        try{
            product1 = service.updateProduct(id,product,imageFile);//Update the product
           
        }catch(IOException e){//Catch any errors
            return new ResponseEntity<>("Update Error",HttpStatus.BAD_REQUEST);//Return a bad request status
        
        }
        if(product1==null){
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("Product updated",HttpStatus.OK);
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProducts(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        service.deleteProducts(id);
        return new ResponseEntity<>("Product Removed", HttpStatus.OK);
        
    }

    @GetMapping("/products/Search")
    public ResponseEntity<List<Product>> searchProducts(String keyWord) {
        System.out.println("Searching Keyword: "+keyWord);
        List<Product> products = service.searchProducts(keyWord);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    
}
