package com.site.bemystory.repository;

import com.site.bemystory.domain.Image;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class ImageRepository {
    private final EntityManager em;

    public ImageRepository(EntityManager em) {
        this.em = em;
    }

    public Image save(Image image){
        em.persist(image);
        return image;
    }

    public Optional<Image> findById(Long id){
        Image image = em.find(Image.class, id);
        return Optional.ofNullable(image);
    }
}
