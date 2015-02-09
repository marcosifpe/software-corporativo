/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author MASC
 */
public class CompradorUtil {

    private static final Logger logger = Logger.getGlobal();

    public Comprador inserirComprador(EntityManager em) {
        Comprador comprador = criarComprador();
        EntityTransaction et = em.getTransaction();
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(comprador);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                logger.log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                logger.info("Transação cancelada.");
            }
        }

        return comprador;
    }

    private Comprador criarComprador() {
        Comprador comprador = new Comprador();
        comprador.setNome("Sicrano da Silva");
        comprador.setEmail("sicrano@gmail.com");
        comprador.setLogin("sicrano1");
        comprador.setSenha("sicrano123");
        comprador.setCpf("534.585.765-40");
        comprador.addTelefone("(81) 3456-2525");
        comprador.addTelefone("(81) 9122-4528");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        comprador.setDataNascimento(c.getTime());
        criarEndereco(comprador);
        CartaoCredito cartaoCredito = criarCartaoCredito();
        comprador.setCartaoCredito(cartaoCredito);

        return comprador;
    }

    public void criarEndereco(Usuario usuario) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Iolanda Rodrigues Sobral");
        endereco.setBairro("Iputinga");
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setCep("50690-220");
        endereco.setNumero(550);
        usuario.setEndereco(endereco);
    }

    public CartaoCredito criarCartaoCredito() {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setBandeira("VISA");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cartaoCredito.setDataExpiracao(c.getTime());
        cartaoCredito.setNumero("120000-100");
        return cartaoCredito;
    }
}
