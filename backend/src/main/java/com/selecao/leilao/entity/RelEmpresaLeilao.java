package com.selecao.leilao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RelEmpresaLeilao implements Serializable {

    @Column
    public Integer empresa;

    @Column
    public Integer leilao;

}
