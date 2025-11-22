package kz.bitlab.g139market.service;

import kz.bitlab.g139market.dto.ProductDto;
import kz.bitlab.g139market.entity.Product;
import kz.bitlab.g139market.mapper.ProductMapper;
import kz.bitlab.g139market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
//        List<ProductResponseDto> dtoList = new ArrayList<>();
//        for (Product product : products) {
//            ProductResponseDto dto = ProductResponseDto
//                    .builder()
//                    .id(product.getId())
//                    .name(product.getName())
//                    .categoryId(product.getCategory().getId())
//                    .brandId(product.getBrand().getId())
//                    .price(product.getPrice())
//                    .color(product.getColor())
//                    .size(product.getSize())
//                    .build();
//            dtoList.add(dto);
//        }
        return ProductMapper.INSTANCE.toDtoList(products);
    }

    public ProductDto create(ProductDto productDto) {
        Product product = ProductMapper.INSTANCE.toEntity(productDto);
        Product newProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDto(newProduct);
    }
}
