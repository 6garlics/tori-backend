package com.site.bemystory.repository;

import com.site.bemystory.domain.Text;
import com.site.bemystory.dto.TextDTO;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class TextRepository {
    private final EntityManager em;

    public Text save(Text text){
        em.persist(text);
        return text;
    }

    public Optional<Text> findById(Long id){
        Text text = em.find(Text.class, id);
        return Optional.ofNullable(text);
    }



}
