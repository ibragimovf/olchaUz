package uz.pdp.olchauz.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.olchauz.model.entity.CategoryEntity;
import uz.pdp.olchauz.model.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> getCategory() {
        return categoryRepository.findAll();
    }

    public List<CategoryEntity> getCategory(Integer pageSize) {
        Pageable page = PageRequest.of(pageSize - 1, 6, Sort.by("id").descending());
        return categoryRepository.findAll(page).stream().toList();
    }

    public void addCategory(Optional<String> name, Optional<Long> parentId) {
        CategoryEntity category = new CategoryEntity();
        name.ifPresent(category::setName);
        parentId.ifPresent(category::setParentId);
        categoryRepository.save(category);
    }

    public void editCategory(Optional<Long> id, Optional<String> name, Optional<Long> parentId) {
        if (id.isPresent()) {
            Optional<CategoryEntity> byId = categoryRepository.findById(id.get());
            if (byId.isPresent()) {
                CategoryEntity category = byId.get();
                name.ifPresent(category::setName);
                parentId.ifPresent(category::setParentId);
                categoryRepository.save(category);
            }
        }
    }

    public void deleteCategory(Optional<Long> id) {
        id.ifPresent(categoryRepository::deleteById);
    }

    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }
}
