package uz.pdp.olchauz.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.olchauz.model.dto.receive.ProductReceiveDto;
import uz.pdp.olchauz.model.entity.CategoryEntity;
import uz.pdp.olchauz.model.entity.ProductEntity;
import uz.pdp.olchauz.model.repository.CategoryRepository;
import uz.pdp.olchauz.model.repository.ProductRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final static String PARENT_URL = "./src/main/resources/static";
    private final static String IMAGE_PNG = "image/png";
    private final static String IMAGE_JPEG = "image/jpeg";
    private final static String IMAGE_JPG = "image/jpd";
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductEntity> getProduct(Integer pageSize) {
        Pageable page = PageRequest.of(pageSize - 1, 6, Sort.by("id").descending());
        return productRepository.findAll(page).stream().toList();
    }

    public ProductEntity getProduct(String name) {
        return productRepository.findById(Long.valueOf(name)).orElseThrow();
    }

    public void addProduct(final ProductReceiveDto productReceiveDto) throws IOException {
        final String imageUrl = saveImage(productReceiveDto.getImage());
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productReceiveDto.getName());
        productEntity.setPrice(productReceiveDto.getPrice());
        Optional<CategoryEntity> byId = categoryRepository.findById(productReceiveDto.getCategoryId());
        byId.ifPresent(productEntity::setCategory);
        productEntity.setImgUrl(imageUrl);
        productRepository.save(productEntity);
    }

    private String saveImage(final MultipartFile multipartFile) throws IOException {
        final File file = new File(PARENT_URL);
        if (file.isDirectory() && file.exists()) {
            final String URL = "C:\\Java\\exam\\olchaUz\\src\\main\\resources\\static\\" + getImageUrl() + getContentType(multipartFile.getContentType());
            final File file1 = new File(file, URL);
            file1.createNewFile();
            writeImage(file1, multipartFile);
            return URL;
        } else {
            throw new FileNotFoundException();
        }
    }

    private String getImageUrl() {
        return UUID.randomUUID().toString();
    }

    private String getContentType(final String contentType) {
        if (contentType.equals(IMAGE_JPEG)) {
            return ".jpeg";
        } else if (contentType.equals(IMAGE_PNG)) {
            return ".png";
        } else if (contentType.equals(IMAGE_JPG)) {
            return ".jpg";
        }
        return null;
    }

    private void writeImage(final File file, final MultipartFile multipartFile) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
    }

    public void editProduct(Optional<Long> id, Optional<String> name, Optional<Double> price) {
        if (id.isPresent()) {
            Optional<ProductEntity> byId = productRepository.findById(id.get());
            if (byId.isPresent()) {
                ProductEntity product = byId.get();
                name.ifPresent(product::setName);
                price.ifPresent(product::setPrice);
                productRepository.save(product);
            }
        }
    }

    public void deleteProduct(Optional<Long> id) {
        id.ifPresent(productRepository::deleteById);
    }

    public List<ProductEntity> getProducts(String name) {
        return productRepository.findByCategory_Id(Long.valueOf(name));
    }
}
