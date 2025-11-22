package kz.bitlab.g139market.mapper;

import kz.bitlab.g139market.dto.ProductDto;
import kz.bitlab.g139market.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "brand.id", target = "brandId")
    ProductDto toDto(Product entity);

    List<ProductDto> toDtoList(List<Product> entities);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "brandId", target = "brand.id")
    Product toEntity(ProductDto dto);
}
