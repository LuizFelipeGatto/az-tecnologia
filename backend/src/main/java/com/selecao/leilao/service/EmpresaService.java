package com.selecao.leilao.service;

import com.selecao.leilao.business.EmpresaBusiness;
import com.selecao.leilao.dto.EmpresaDTO;
import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.entity.Empresa;
import com.selecao.leilao.specification.filter.Filtro;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/empresa")
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaBusiness empresaBusiness;

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> listaEmpresas() {
        return ResponseEntity.ok(empresaBusiness.getEmpresasDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoOperacaoDTO<EmpresaDTO>> empresaById(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaBusiness.getById(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filtro(@RequestBody Filtro filtro) {
        return ResponseEntity.ok(empresaBusiness.filtroIsEmpty(filtro) ?
                empresaBusiness.listarTodos() :
                empresaBusiness.findByFilter(filtro));
    }

    @PostMapping
    public ResponseEntity<ResultadoOperacaoDTO<String>> registrarEmpresa(@RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaBusiness.salvarEmpresa(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoOperacaoDTO<String>> atualizarEmpresa(@RequestBody EmpresaDTO empresaDTO, @PathVariable Integer id){
        return ResponseEntity.ok(empresaBusiness.editarEmpresa(empresaDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultadoOperacaoDTO<String>> deletarEmpresa(@PathVariable Integer id){
        return ResponseEntity.ok(empresaBusiness.deletarEmpresa(id));
    }

}
