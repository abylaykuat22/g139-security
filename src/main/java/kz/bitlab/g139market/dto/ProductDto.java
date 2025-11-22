package kz.bitlab.g139market.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductDto {

    Long id;

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    String name;

    @NotNull(message = "Category ID is required")
    Long categoryId;

    @NotNull(message = "Brand ID is required")
    Long brandId;
    Double price;

    @NotEmpty(message = "Color cannot be empty")
    String color;

    String size;
}
