package br.com.zupacademy.propostas.api.cartao;

import br.com.zupacademy.propostas.api.analise.AnaliseForm;
import br.com.zupacademy.propostas.api.cartao.client.CartaoClient;
import br.com.zupacademy.propostas.proposta.EnumEstadoProposta;
import br.com.zupacademy.propostas.proposta.Proposta;
import br.com.zupacademy.propostas.proposta.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AssociaCartao {

    Logger LOGGER = LoggerFactory.getLogger(AssociaCartao.class);

    @Autowired
    private CartaoClient cartaoClient;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    @Scheduled(fixedRateString = "${associar.cartao.tempo.schedule}")
    public void associa() {
        Proposta proposta = propostaRepository.findByEstadoPropostaAndNumCartaoIsNull(EnumEstadoProposta.ELEGIVEL);

        if (proposta == null) {
            return;
        }

        try {
            AnaliseForm analiseForm = new AnaliseForm(proposta.getDocumento(), proposta.getNome(), proposta.getId());
            Cartao cartao = cartaoClient.associarCartao(analiseForm);
            proposta.associaCartao(cartao);

            cartaoRepository.save(cartao);
            LOGGER.info("Cartão {} associado com sucesso!", cartao.getIdCartao());
            propostaRepository.save(proposta);
        } catch (FeignException exception) {
            LOGGER.error("Erro na rotina de associar cartão: {}", exception.getMessage());
            exception.getMessage();
        }
    }
}