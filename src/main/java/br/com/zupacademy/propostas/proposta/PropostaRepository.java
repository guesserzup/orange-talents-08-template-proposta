package br.com.zupacademy.propostas.proposta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Proposta findByEstadoPropostaAndNumCartaoIsNull(EnumEstadoProposta estado);
    Proposta findByDocumento(String documento);
    List<Proposta> findAllByDocumento(String Documento);
}
