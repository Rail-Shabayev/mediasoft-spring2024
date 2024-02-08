package org.rail.spring2024.controller;

import lombok.RequiredArgsConstructor;
import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public String postProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }
}
