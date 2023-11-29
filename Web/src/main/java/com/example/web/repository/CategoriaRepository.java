package com.example.web.repository;

import com.example.web.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query
    public List<Categoria> findAllByOrderByIdAsc();

    @Query
    public List<Categoria> findAllByNomeContainingIgnoreCaseOrderByIdAsc(String nome);
}
