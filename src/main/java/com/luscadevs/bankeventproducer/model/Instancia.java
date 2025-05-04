package com.luscadevs.bankeventproducer.model;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Instancia {
    private String idInstancia;
    private String idJornada;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private StatusInsntaciaEnum status;
}
