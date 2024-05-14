package com.selecao.leilao.service;

import com.selecao.leilao.business.LoteBusiness;
import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.entity.Lote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lote")
@RequiredArgsConstructor
public class LoteService {

    private final LoteBusiness loteBusiness;

    @GetMapping
    public ResponseEntity<List<Lote>> listaLotes() {
        return ResponseEntity.ok(loteBusiness.getLotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoOperacaoDTO<Lote>> loteById(@PathVariable Integer id) {
        return ResponseEntity.ok(loteBusiness.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResultadoOperacaoDTO<String>> registrarLote(@RequestBody Lote lote) {
        return ResponseEntity.ok(loteBusiness.salvarLote(lote));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoOperacaoDTO<String>> atualizarLote(@RequestBody Lote lote, @PathVariable Integer id){
        return ResponseEntity.ok(loteBusiness.editarLote(lote, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultadoOperacaoDTO<String>> deletarLote(@PathVariable Integer id){
        return ResponseEntity.ok(loteBusiness.deletarLote(id));
    }

}
