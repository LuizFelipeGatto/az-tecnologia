package com.selecao.leilao.service;

import com.selecao.leilao.business.LeilaoBusiness;
import com.selecao.leilao.dto.LeilaoDTO;
import com.selecao.leilao.specification.filter.Filtro;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leilao")
@RequiredArgsConstructor
public class LeilaoService {

    private final LeilaoBusiness leilaoBusiness;

    @GetMapping
    public ResponseEntity<List<LeilaoDTO>> listaLeiloes(){
        return ResponseEntity.ok(leilaoBusiness.leiloes());
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filtro(@RequestBody Filtro filtro) {
        return ResponseEntity.ok(leilaoBusiness.filtroIsEmpty(filtro) ?
                        leilaoBusiness.leiloes() :
                        leilaoBusiness.findByFilter(filtro));
    }

}
