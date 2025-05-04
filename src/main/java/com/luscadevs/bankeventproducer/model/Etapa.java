package com.luscadevs.bankeventproducer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Etapa {
    private String nome; // Nome da etapa (ex.: "Elegibilidade", "Simulação")
    private String descricao; // Descrição da etapa
    private int ordem; // Ordem da etapa (ex.: "1", "2", "3")
}