package com.selecao.leilao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoOperacaoDTO<T> {

    private boolean sucesso;
    private T resultado;
    private String mensagemErro;

}
