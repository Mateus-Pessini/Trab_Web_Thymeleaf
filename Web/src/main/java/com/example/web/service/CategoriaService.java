package com.example.web.service;

import com.example.web.domain.Categoria;
import com.example.web.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public void insert(Categoria categoria) {
        categoriaRepository.saveAndFlush(categoria);
    }

    public void edit (Categoria categoria) {
        categoriaRepository.saveAndFlush(categoria);
    }

    public List<Categoria> listAll() {
        return categoriaRepository.findAllByOrderByIdAsc();
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).get();
    }

    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

    public List<Categoria> listByFilter(String nome) {
        if (nome.isEmpty()) {
            return categoriaRepository.findAll();
        } else {
            return categoriaRepository.findAllByNomeContainingIgnoreCaseOrderByIdAsc(nome);
        }
    }

}
