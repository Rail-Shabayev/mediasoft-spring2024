package org.rail.spring2024.controller;

import lombok.RequiredArgsConstructor;
import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController extends ProductApi {
    private final ProductService productService;
    @GetMapping
    public List<ProductDTO> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public String postProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/{name}")
    @ResponseStatus(CREATED)
    public String putProduct(@RequestBody ProductDTO productDTO, @PathVariable("name") String name) {
        return productService.putProduct(name, productDTO);
    }

    @DeleteMapping("/{name}")
    public String deleteProduct(@PathVariable("name") String productName) {
        return productService.deleteProduct(productName);
    }
}
