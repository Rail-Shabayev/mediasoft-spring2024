package org.rail.spring2024.controller;

import lombok.RequiredArgsConstructor;
import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.exception.ProductNotFoundException;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Controller class that holds CRUD operations for product entity in /api/product endpoint
 * <p>
 * Не знаю можно ли использовать поле <b>name</b> из {@link Product} чтобы совершать запросы, но мне
 * это показалось лучше, чем использовать <b>uuid</b>))) Надеюсь, это не особо критично и если что <b>name</b> можно
 * легко поменять на <b>uuid</b> (・ω・)
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
     * @return list of found {@link ProductDTO} objects
     */
    @GetMapping
    public List<ProductDTO> getProducts() {
        return productService.getAllProducts();
    }

    /**
     * POST method for /api/product endpoint
     * @param productDTO {@link ProductDTO} object passed by a user
     * @return string with operation work status
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public String postProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    /**
     * PUT method for /api/product/ endpoint
     * @param productDTO productDTO {@link ProductDTO} object passed by a user
     * @param name name of the {@link Product} that user wants to update
     * @return string with operation work status
     * @throws ProductNotFoundException if {@link Product} with provided name is not found in database
     */
    @PutMapping("/{name}")
    @ResponseStatus(CREATED)
    public String putProduct(@RequestBody ProductDTO productDTO, @PathVariable("name") String name) throws ProductNotFoundException {
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
}
