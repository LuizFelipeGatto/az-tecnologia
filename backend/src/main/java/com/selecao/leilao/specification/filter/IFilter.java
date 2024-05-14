package com.selecao.leilao.specification.filter;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface IFilter {

    Boolean getSimplificado();

    void setSimplificado(Boolean value);

    Integer getPagina();

    void setPagina(Integer value);

    Integer getTamanhoPagina();

    void setTamanhoPagina(Integer value);

    String getOrdem();

    void setOrdem(String value);

    List<String> getOrdemList();

    void setOrdemList(List<String> values);

    Sort.Direction getOrdemDirecao();

    void setOrdemDirecao(Sort.Direction value);

}
