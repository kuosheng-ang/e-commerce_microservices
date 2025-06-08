package com.ecommerce.product.controllers;

import com.ecommerce.product.config.AppConstants;
import com.ecommerce.product.dtos.*;
import com.ecommerce.product.models.Category;
import com.ecommerce.product.services.CategoryService;
import com.ecommerce.product.services.Impl.ProductServiceImpl;
import com.ecommerce.product.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.GetExchange;

import java.io.IOException;
import java.util.List;

@Slf4j
@Tag(
        name = "CRUD REST APIs for Products within kuoshengclement ECommerce",
        description = "CRUD REST APIs in kuoshengclement ECommerce to CREATE, UPDATE, FETCH AND DELETE Product Details"
)
@Validated
@RestController
@RequestMapping(value="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProductController {


    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private ProductConfigInfoDto productConfigInfoDto;

    private static Logger productlogger = LoggerFactory.getLogger(ProductController.class);



    @GetMapping("/simulate")
    public ResponseEntity<String> simulateFailure(
            @RequestParam(defaultValue = "false") boolean fail) {
        if (fail) {
            throw new RuntimeException("Simulated Failure For Testing");
        }
        return ResponseEntity.ok("Product Service is OK");
    }

    @PostMapping("/admin/product/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/products/{id}")    // expose to other microservice
    public ResponseEntity<ProductResponse> getProductById(
                                    @PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @GetMapping("/public/products/fetchlist")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder, keyword, category);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
                                                                @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    /**   admin API **/

    @PostMapping("/admin/product/{categoryId}/add")   // API Endpoint - tested working using Postman
    public ResponseEntity<ProductRequest> addProduct(@RequestBody ProductRequest productRequest, @PathVariable int categoryId){
        ProductRequest savedProductDTO = productService.addProduct(categoryId, productRequest);
        if (productRequest.isCreated) {


            productlogger.info("product: " + productRequest.getName() + "created successfully");
            return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);

        } else {
            productlogger.error("product: "  + productRequest.getName() + " is created unsuccessfully. Please check the details again");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/admin/products/updateProduct")
    //public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
    //                                                @PathVariable Long productId){
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO ){

        /*return productService.updateProduct(productId, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());*/
        ProductDTO updatedProductDTO = productService.updateProduct( productDTO, productDTO.getProductName());
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }



    @DeleteMapping("/admin/products/delete/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO deletedProduct= productService.deleteProduct(productId);
        //return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);

    }

    /*@GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }*/

    @PutMapping("/admin/products/updateImage/{productId}")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/public/products/config/info")
    public ResponseEntity<ProductConfigInfoDto> getConfigInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productConfigInfoDto);
    }
}
