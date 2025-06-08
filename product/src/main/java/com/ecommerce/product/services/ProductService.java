package com.ecommerce.product.services;

import com.ecommerce.product.dtos.ProductRequest;
import com.ecommerce.product.exceptions.APIException;
import com.ecommerce.product.exceptions.ResourceNotFoundException;
import com.ecommerce.product.models.Cart;
import com.ecommerce.product.models.Category;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.dtos.CartDTO;
import com.ecommerce.product.dtos.ProductDTO;
import com.ecommerce.product.dtos.ProductResponse;
import com.ecommerce.product.repositories.CartRepository;
import com.ecommerce.product.repositories.CategoryRepository;
import com.ecommerce.product.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface ProductService {


    ProductRequest addProduct(Integer categoryId, ProductRequest product);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword, String category);

    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(ProductDTO product, String productName);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    Optional<ProductResponse> getProductById(String id);


    Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest);

    //List<ProductDTO> deleteProductList (List<ProductDTO> productDtos);
}
