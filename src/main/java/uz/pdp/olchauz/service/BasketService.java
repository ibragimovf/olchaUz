package uz.pdp.olchauz.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.olchauz.model.entity.BasketEntity;
import uz.pdp.olchauz.model.repository.BasketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public List<BasketEntity> getBaskets(
            Integer pageSize
    ) {
        Pageable page = PageRequest.of(pageSize - 1, 6, Sort.by("id").descending());
        return basketRepository.findAll(page).stream().toList();
    }

    public void delivered(Optional<Long> id) {
        if (id.isPresent()) {
            Optional<BasketEntity> byId = basketRepository.findById(id.get());
            if (byId.isPresent()) {
                BasketEntity basketEntity = byId.get();
                basketRepository.save(basketEntity);
            }
        }
    }
}

