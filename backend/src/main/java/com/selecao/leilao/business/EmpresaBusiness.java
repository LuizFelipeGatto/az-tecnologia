package com.selecao.leilao.business;

import com.selecao.leilao.dto.EmpresaDTO;
import com.selecao.leilao.entity.Empresa;
import com.selecao.leilao.repository.EmpresaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmpresaBusiness {

    private final EmpresaRepository empresaRepository;

    public List<Empresa> getEmpresas(){
        return empresaRepository.findAll();
    }

    public List<EmpresaDTO> getEmpresasDTO(){
        List<Empresa> empresas = empresaRepository.findAll();
        return empresas.stream().map(empresa -> new EmpresaDTO(empresa)).collect(Collectors.toList());
    }

    public EmpresaDTO getById(Integer id){
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if(empresa.isEmpty()) {
            return null;
        }
        return new EmpresaDTO(empresa.get());
    }

    @Transactional
    public EmpresaDTO salvarEmpresa(Empresa empresa){
        Empresa empresaSalva = empresaRepository.save(empresa);
        return new EmpresaDTO(empresaSalva);
    }

    @Transactional
    public EmpresaDTO editarEmpresa(EmpresaDTO empresaDTO, Integer id){
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if(empresa.isEmpty()){
            return null;
        }
        empresaDTO.setId(empresa.get().getId());
        Empresa empresaSalvar = atualizaEmpresa(empresaDTO);
        empresaSalvar.setCreatedAt(empresa.get().getCreatedAt());

        return new EmpresaDTO(empresaRepository.save(empresaSalvar));
    }

    public Empresa atualizaEmpresa(EmpresaDTO empresaDTO){
        return Empresa
                .builder()
                .id(empresaDTO.getId())
                .razaoSocial(empresaDTO.getRazaoSocial())
                .cnpj(empresaDTO.getCnpj())
                .email(empresaDTO.getEmail())
                .site(empresaDTO.getSite())
                .usuario(empresaDTO.getUsuario())
                .senha(empresaDTO.getSenha())
                .cep(empresaDTO.getCep())
                .municipio(empresaDTO.getMunicipio())
                .bairro(empresaDTO.getBairro())
                .lograduro(empresaDTO.getLograduro())
                .numero(empresaDTO.getNumero())
                .complemento(empresaDTO.getComplemento())
                .telefone(empresaDTO.getTelefone())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public String deletarEmpresa(Integer id){
        empresaRepository.findById(id).ifPresent(empresaRepository::delete);
        return "ok";
    }

}
