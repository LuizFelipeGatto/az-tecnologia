package com.selecao.leilao.business;

import com.selecao.leilao.dto.EmpresaDTO;
import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.entity.Empresa;
import com.selecao.leilao.repository.CompradorRepository;
import com.selecao.leilao.repository.EmpresaRepository;
import com.selecao.leilao.repository.LeilaoRepository;
import com.selecao.leilao.specification.EmpresaSpecification;
import com.selecao.leilao.specification.filter.Filtro;
import com.selecao.leilao.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmpresaBusiness {

    private final EmpresaRepository empresaRepository;

    private final LeilaoRepository leilaoRepository;

    private final CompradorRepository compradorRepository;

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

    public Page<Empresa> findByFilter(Filtro filtro) {
        EmpresaSpecification empresaSpecification = new EmpresaSpecification();
        Specification<Empresa> tranSpec = empresaSpecification.build(filtro);
        Pageable pageable = empresaSpecification.buildPageable(filtro);
        return empresaRepository.findAll(tranSpec, pageable);
    }

    public List<Empresa> listarTodos () {
        return empresaRepository.findAll();
    }

    public boolean filtroIsEmpty(Filtro filtro){
        if(StringUtils.hasText(filtro.getCnpj())
                || Objects.nonNull(filtro.getComprador())
                || StringUtils.hasText(filtro.getTelefone())
                || StringUtils.hasText(filtro.getEmail())) {
            return false;
        }

        return true;
    }

    @Transactional
    public EmpresaDTO salvarEmpresa(Empresa empresa){
        empresa.setCreatedAt(LocalDateTime.now());
        empresa.setUpdatedAt(LocalDateTime.now());
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

    public ResultadoOperacaoDTO<String> deletarEmpresa(Integer id){
        if(compradorRepository.vinculado(id)){
            return new ResultadoOperacaoDTO<>(false, null, Constants.EMPRESA_VINCULADA);
        }

        empresaRepository.findById(id).ifPresent(empresaRepository::delete);
        return new ResultadoOperacaoDTO<>(true, Constants.EXCLUIDA, null);
    }

}
