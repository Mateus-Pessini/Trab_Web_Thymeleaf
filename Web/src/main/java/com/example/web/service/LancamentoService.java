package com.example.web.service;

import com.example.web.Enum.TipoLancamento;
import com.example.web.domain.Lancamento;
import com.example.web.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public void insert(Lancamento lancamento) {
        lancamentoRepository.saveAndFlush(lancamento);
    }

    public void edit (Lancamento lancamento) {
        lancamentoRepository.saveAndFlush(lancamento);
    }

    public List<Lancamento> listAll() {
        return lancamentoRepository.findAllByOrderByIdAsc();
    }

    public Lancamento findById(Long id) {
        return lancamentoRepository.findById(id).get();
    }

    public void delete(Long id) {
        lancamentoRepository.deleteById(id);
    }

    public List<Lancamento> listByFilter(LocalDate dataInicial, LocalDate dataFinal, TipoLancamento tipo) {
        return lancamentoRepository.findByFilter(dataInicial, dataFinal, tipo);
    }

    public List<Lancamento> listByTipo(TipoLancamento tipo) {
        return lancamentoRepository.findAllByTipo(tipo);
    }
}
