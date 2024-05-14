package com.selecao.leilao.service;

import com.selecao.leilao.business.UnidadeBusiness;
import com.selecao.leilao.dto.UnidadeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidade")
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeBusiness unidadeBusiness;

    @GetMapping
    public ResponseEntity<List<UnidadeDTO>> listaUnidades(){
        return ResponseEntity.ok(unidadeBusiness.getUnidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeDTO> buscaPorId(@PathVariable Integer id){
        return ResponseEntity.ok(unidadeBusiness.getById(id));
    }

    @PostMapping
    public ResponseEntity<UnidadeDTO> registrarUnidade(@RequestBody UnidadeDTO unidadeDTO){
        return ResponseEntity.ok(unidadeBusiness.salvarUnidade(unidadeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeDTO> atualizarUnidade(@RequestBody UnidadeDTO unidadeDTO, @PathVariable Integer id){
        return ResponseEntity.ok(unidadeBusiness.editarUnidade(unidadeDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id){
        return ResponseEntity.ok(unidadeBusiness.deletarUnidade(id));
    }


}
