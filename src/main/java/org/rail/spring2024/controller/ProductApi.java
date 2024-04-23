package org.rail.spring2024.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rail.spring2024.dto.ProductDto;
import org.rail.spring2024.exception.ProductNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * This class is extended by {@link ProductController} to eliminate clutter in that class.
 * It provides api specification for {@link ProductController} class
 */
@Tag(name = "Product", description = "Api of Product")
public abstract class ProductApi {
    /**
     * provides api specification for GET method
     */
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}")))
    })
    public abstract List<ProductDto> getProducts();

    /**
     * provides api specification for POST method
     */
    @Operation(summary = "Add a new product to the storehouse")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Product created",
                    responseCode = "201",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(example = "{\"product saved\"}"))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}"))),
    })
    public abstract String postProduct(
            @Parameter(description = "Product object that needs to be saved", required = true)
            @RequestBody ProductDto productDTO) throws ProductNotFoundException;

    /**
     * provides api specification for PUT method
     */
    @Operation(summary = "Update a product in the storehouse")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Product created",
                    responseCode = "201",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(example = "{\"product updated\"}"))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}"))),
            @ApiResponse(
                    description = "Product not found",
                    responseCode = "404",
                    content = @Content(schema = @Schema(example = "{Product not found with name: laptop}")))
    })
    public abstract String putProduct(
            @Parameter(required = true, description = "Object to be updated")
            @RequestBody ProductDto productDTO,
            @Parameter(required = true, description = "Name of the object that needs to be updated")
            @PathVariable("name") String name)
            throws ProductNotFoundException;

    /**
     * provides api specification for DELETE method
     */
    @Operation(summary = "Delete a product from the storehouse")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(example = "{\"product deleted\"}"))),
            @ApiResponse(
                    description = "Bad request",
                    responseCode = "400",
                    content = @Content(schema = @Schema(example = "{\"name\":\"string\"}"))),
            @ApiResponse(
                    description = "Product not found",
                    responseCode = "404",
                    content = @Content(schema = @Schema(example = "{Product not found with name: laptop}"))),
    })
    public abstract String deleteProduct(
            @Parameter(required = true, description = "Name of the object that needs to be deleted")
            @PathVariable("name") String name)
            throws ProductNotFoundException;
}
