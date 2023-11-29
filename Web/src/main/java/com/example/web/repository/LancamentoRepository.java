package com.example.web.repository;

import com.example.web.Enum.TipoLancamento;
import com.example.web.domain.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query
    public List<Lancamento> findAllByOrderByIdAsc();

    @Query("SELECT l FROM Lancamento l WHERE l.categoria.tipo = :tipo")
    public List<Lancamento> findAllByTipo(@Param("tipo") TipoLancamento tipo);

    @Query("SELECT l FROM Lancamento l " +
            "WHERE (:dataInicial IS NULL OR l.data >= :dataInicial) " +
            "AND (:dataFinal IS NULL OR l.data <= :dataFinal) " +
            "AND (:tipo IS NULL OR l.categoria.tipo = :tipo)")
    List<Lancamento> findByFilter(
            @Param("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @Param("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
            @Param("tipo") TipoLancamento tipo);

}