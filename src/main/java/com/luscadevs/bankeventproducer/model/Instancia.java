package com.luscadevs.bankeventproducer.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Instancia {
    private String idInstancia;
    private String idJornada;
    private int idProduto;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private String etapaAtual;
    private StatusInsntaciaEnum status;
}
