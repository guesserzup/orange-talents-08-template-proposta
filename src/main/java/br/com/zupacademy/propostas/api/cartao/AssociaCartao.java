package br.com.zupacademy.propostas.api.cartao;

import br.com.zupacademy.propostas.api.cartao.connector.CartaoConnector;
import br.com.zupacademy.propostas.proposta.Proposta;
import br.com.zupacademy.propostas.proposta.PropostaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AssociaCartao {

    @Autowired
    private CartaoConnector cartaoConnector;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Scheduled(fixedRateString = "${associar.cartao.tempo.schedule}")
    public void associa() {
        List<Proposta> listaPropostas = propostaRepository.findAllByEstadoPropostaAndNumCartaoIsNull("ELEGIVEL");

        listaPropostas.forEach(proposta -> {
            HashMap<String, String> dadosProposta = new HashMap<>();
            dadosProposta.put("documento", proposta.getDocumento());
            dadosProposta.put("nome", proposta.getNome());
            dadosProposta.put("idProposta", proposta.getId().toString());

            try {
                Cartao cartao = cartaoConnector.associarCartao(dadosProposta);
                proposta.setNumCartao(cartao.getNumCartao());

                propostaRepository.save(proposta);
                cartaoRepository.save(cartao);
            } catch (FeignException exception) {
                exception.getMessage();
            }
        });
    }
}