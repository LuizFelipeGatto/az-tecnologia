package com.selecao.leilao.specification.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Filtro implements IFilter{

    private String cnpj;
    private String razaoSocial;
    private String telefone;
    private String email;
    private LocalDateTime inicioPrevisto;
    private Integer comprador;
    private BigDecimal totalLeilao;
    private String campoSimplificado = "";

    private Boolean simplificado = false;
    private Integer pagina = 0;
    private Integer tamanhoPagina = 5;
    private String ordem = "id";
    private List<String> ordemList;
    private Sort.Direction ordemDirecao = Sort.Direction.DESC;

}
