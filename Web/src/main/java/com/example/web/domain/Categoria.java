package com.example.web.domain;


import com.example.web.Enum.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "CATEGORIA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 60)
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

}