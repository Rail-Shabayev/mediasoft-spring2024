package org.rail.spring2024.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rail.spring2024.dto.ProductDto;
import org.rail.spring2024.dto.filterDto.ProductFilterDto;
import org.rail.spring2024.exception.ProductNotFoundException;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Controller class that holds CRUD operations for product entity in /api/product endpoint
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController extends ProductApi {
    /**
     * Product service object for working with business logic.
     */
    private final ProductService productService;

    /**
     * GET method for /api/product endpoint
     * @return list of found {@link ProductDto} objects
     */
    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getAllProducts();
    }

    /**
     * POST method for /api/product endpoint
     * @param productDTO {@link ProductDto} object passed by a user
     * @return string with operation work status
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public String postProduct(@RequestBody ProductDto productDTO) {
        return productService.saveProduct(productDTO);
    }

    /**
     * PUT method for /api/product/ endpoint
     * @param productDTO productDTO {@link ProductDto} object passed by a user
     * @param name name of the {@link Product} that user wants to update
     * @return string with operation work status
     * @throws ProductNotFoundException if {@link Product} with provided name is not found in database
     */
    @PutMapping("/{name}")
    @ResponseStatus(CREATED)
    public String putProduct(@RequestBody ProductDto productDTO, @PathVariable("name") String name) throws ProductNotFoundException {
        return productService.putProduct(name, productDTO);
    }

    /**
     * DELETE method for /api/product/ endpoint
     * @param productName name of the {@link Product} that user wants to delete
     * @return string with operation work status
     * @throws ProductNotFoundException if {@link Product} with provided name is not found in database
     */
    @DeleteMapping("/{name}")
    public String deleteProduct(@PathVariable("name") String productName) throws ProductNotFoundException {
        return productService.deleteProduct(productName);
    }


    @PostMapping("/search")
    public List<ProductDto> search(Pageable pageable, @Valid @RequestBody List<ProductFilterDto> filterDto) {
        return productService.searchProduct(pageable, filterDto);
    }
}
