package com.selecao.leilao.service;

import com.selecao.leilao.business.EmpresaBusiness;
import com.selecao.leilao.dto.EmpresaDTO;
import com.selecao.leilao.entity.Empresa;
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
    public ResponseEntity<EmpresaDTO> empresaById(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaBusiness.getById(id));
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> registrarEmpresa(@RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaBusiness.salvarEmpresa(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> atualizarEmpresa(@RequestBody EmpresaDTO empresaDTO, @PathVariable Integer id){
        return ResponseEntity.ok(empresaBusiness.editarEmpresa(empresaDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEmpresa(@PathVariable Integer id){
        return ResponseEntity.ok(empresaBusiness.deletarEmpresa(id));
    }

}
