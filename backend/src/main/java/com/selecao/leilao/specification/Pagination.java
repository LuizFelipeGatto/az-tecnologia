package com.selecao.leilao.specification;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
public class Pagination {

    private Integer numeroPagina;
    private int tamanhoPagina;
    private String ordem;
    private List<String> ordemList;
    private Sort.Direction ordemDirecao = Sort.Direction.ASC;

    public Pagination(Integer numeroPagina, Integer tamanhoPagina) {

        this.numeroPagina = numeroPagina;
        this.tamanhoPagina = tamanhoPagina == null ? 15 : tamanhoPagina;

    }

    public Pagination(Integer numeroPagina, Integer tamanhoPagina, String ordem, Sort.Direction ordemDirecao) {

        this.numeroPagina = numeroPagina;
        this.tamanhoPagina = tamanhoPagina == null ? 15  : tamanhoPagina;
        this.ordem = ordem;
        this.ordemDirecao = ordemDirecao;

    }

    public Pagination(Integer numeroPagina, Integer tamanhoPagina, List<String> ordemList, Sort.Direction ordemDirecao) {

        this.numeroPagina = numeroPagina;
        this.tamanhoPagina = tamanhoPagina == null ? 15  : tamanhoPagina;
        this.ordemList = ordemList;
        this.ordemDirecao = ordemDirecao;

    }

    public Pageable build() {

        if(numeroPagina == null) {

            return  PageRequest.of(0 , tamanhoPagina, Sort.unsorted());

        }

        if(ordemList != null && !ordemList.isEmpty()) {

            return PageRequest.of(numeroPagina, tamanhoPagina, Sort.by(ordemDirecao, ordemList.toArray(new String[0])));

        } else if(ordem != null && !ordem.isEmpty()) {

            return PageRequest.of(numeroPagina, tamanhoPagina, Sort.by(ordemDirecao, ordem));

        } else {

            return PageRequest.of(numeroPagina, tamanhoPagina);

        }

    }

}
