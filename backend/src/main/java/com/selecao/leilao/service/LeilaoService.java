package com.selecao.leilao.service;

import com.selecao.leilao.business.LeilaoBusiness;
import com.selecao.leilao.dto.LeilaoDTO;
import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.entity.Leilao;
import com.selecao.leilao.specification.filter.Filtro;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> leilaoById(@PathVariable Integer id) {
        ResultadoOperacaoDTO<Leilao> resultadoOperacao = leilaoBusiness.getById(id);
        return !resultadoOperacao.isSucesso() ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultadoOperacao.getMensagemErro()) :
                ResponseEntity.ok(resultadoOperacao.getResultado());
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filtro(@RequestBody Filtro filtro) {
        return ResponseEntity.ok(leilaoBusiness.filtroIsEmpty(filtro) ?
                        leilaoBusiness.leiloes() :
                        leilaoBusiness.findByFilter(filtro));
    }

    @PostMapping
    public ResponseEntity<ResultadoOperacaoDTO<String>> registrarLeilao(@RequestBody Leilao leilao) {
        return ResponseEntity.ok(leilaoBusiness.salvarLeilao(leilao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoOperacaoDTO<String>> atualizarLeilao(@RequestBody Leilao leilao, @PathVariable Integer id){
        return ResponseEntity.ok(leilaoBusiness.editarLeilao(leilao, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultadoOperacaoDTO<String>> deletarLeilao(@PathVariable Integer id){
        return ResponseEntity.ok(leilaoBusiness.deletarLeilao(id));
    }

}
