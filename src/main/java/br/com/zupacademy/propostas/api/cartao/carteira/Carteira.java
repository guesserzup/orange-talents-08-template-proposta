package br.com.zupacademy.propostas.api.cartao.carteira;

import br.com.zupacademy.propostas.api.cartao.Cartao;

import javax.persistence.*;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumCarteira carteira;

    private String idCarteiraLegado;

    @ManyToOne
    @JoinColumn(name = "cartao_id_cartao")
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(EnumCarteira carteira, String idCarteiraLegado, Cartao cartao) {
        this.carteira = carteira;
        this.idCarteiraLegado = idCarteiraLegado;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public EnumCarteira getCarteira() {
        return carteira;
    }

    public String getIdCarteiraLegado() {
        return idCarteiraLegado;
    }

    public Cartao getCartao() {
        return cartao;
    }

    @Override
    public String toString() {
        return "Carteira{" + "id=" + id + ", carteira=" + carteira + ", idCarteiraLegado='" + idCarteiraLegado + '\'' + ", cartao=" + cartao + '}';
    }
}
