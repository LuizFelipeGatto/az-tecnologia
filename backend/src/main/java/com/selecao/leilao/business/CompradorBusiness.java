package com.selecao.leilao.business;

import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.entity.Comprador;
import com.selecao.leilao.repository.CompradorRepository;
import com.selecao.leilao.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompradorBusiness {

    private final CompradorRepository compradorRepository;

    public List<Comprador> getCompras(){
        return compradorRepository.findAll();
    }

    @Transactional
    public Comprador salvarCompra(Comprador comprador){
        Comprador comprador1 = new Comprador();
        comprador1.setEmpresa(comprador.getEmpresa());
        comprador1.setLeilao(comprador.getLeilao());
        return compradorRepository.save(comprador1);
    }

    public ResultadoOperacaoDTO<String> deletarCompra(Integer id, Integer leilao){
        Optional<Comprador> existsCompra = compradorRepository.existsCompra(id, leilao);
        if(existsCompra.isEmpty()) {
            return new ResultadoOperacaoDTO<>(false, null, Constants.COMPRA_NAO_ENCONTRADA);
        }

        existsCompra.ifPresent(compradorRepository::delete);
        return new ResultadoOperacaoDTO<>(true, Constants.EXCLUIDA, null);
    }

}
