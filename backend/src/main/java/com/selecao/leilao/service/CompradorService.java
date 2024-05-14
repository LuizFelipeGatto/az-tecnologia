package com.selecao.leilao.service;

import com.selecao.leilao.business.CompradorBusiness;
import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.entity.Comprador;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comprador")
@RequiredArgsConstructor
public class CompradorService {

    private final CompradorBusiness compradorBusiness;

    @GetMapping
    public ResponseEntity<List<Comprador>> listaComprador(){
        return ResponseEntity.ok(compradorBusiness.getCompras());
    }

    @PostMapping
    public ResponseEntity<Comprador> registrarCompra(@RequestBody Comprador comprador){
        return ResponseEntity.ok(compradorBusiness.salvarCompra(comprador));
    }

    @DeleteMapping("/{id}/{leilao}")
    public ResponseEntity<ResultadoOperacaoDTO<String>> deletarComprador(@PathVariable Integer id, @PathVariable Integer leilao){
        return ResponseEntity.ok(compradorBusiness.deletarCompra(id, leilao));
    }

}
