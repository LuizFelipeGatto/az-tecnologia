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
import com.selecao.leilao.util.DataUtils;
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

    public ResultadoOperacaoDTO<EmpresaDTO> getById(Integer id){
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if(empresa.isEmpty()) {
            return new ResultadoOperacaoDTO<>(false, null, Constants.EMPRESA_NAO_ENCONTRADA);
        }
        return new ResultadoOperacaoDTO<>(false, new EmpresaDTO(empresa.get()), null);
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
    public ResultadoOperacaoDTO<String> salvarEmpresa(Empresa empresa){
        if (empresaRepository.existeEmpresaCnpj(empresa.getCnpj())){
            return new ResultadoOperacaoDTO<>(false, null, Constants.CNPJ_CADASTRADO);
        }

        if (empresaRepository.existeEmpresaUsuario(empresa.getUsuario())){
            return new ResultadoOperacaoDTO<>(false, null, Constants.USUARIO_JA_CADASTRADO);
        }

        empresa.setCreatedAt(LocalDateTime.now());
        empresa.setUpdatedAt(LocalDateTime.now());
        empresa.setSenha(DataUtils.hashPassword(empresa.getSenha()));
        empresaRepository.save(empresa);
        return new ResultadoOperacaoDTO<>(true, Constants.EMPRESA_CADASTRADA, null);
    }

    @Transactional
    public ResultadoOperacaoDTO<String> editarEmpresa(EmpresaDTO empresaDTO, Integer id){
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if(empresa.isEmpty()){
            return null;
        }

        if (empresaRepository.existeEmpresaCnpj(empresaDTO.getCnpj()) && !empresa.get().getCnpj().equals(empresaDTO.getCnpj())){
            return new ResultadoOperacaoDTO<>(false, null, Constants.CNPJ_CADASTRADO);
        }

        if (empresaRepository.existeEmpresaUsuario(empresaDTO.getUsuario()) && !empresa.get().getUsuario().equals(empresaDTO.getUsuario())){
            return new ResultadoOperacaoDTO<>(false, null, Constants.USUARIO_JA_CADASTRADO);
        }

        empresaDTO.setId(empresa.get().getId());
        Empresa empresaSalvar = atualizaEmpresa(empresaDTO);
        empresaSalvar.setCreatedAt(empresa.get().getCreatedAt());
        empresaRepository.save(empresaSalvar);
        return new ResultadoOperacaoDTO<>(true, Constants.EMPRESA_ATUALIZADA, null);
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
