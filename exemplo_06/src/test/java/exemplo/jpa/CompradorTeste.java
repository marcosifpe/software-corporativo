/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masc1
 */
public class CompradorTeste extends Teste {
    @Test
    public void persistirComprador() {
        Comprador comprador = new Comprador();
        comprador.setCpf("526.594.890-25");
        comprador.setLogin("comprador1");
        comprador.setNome("Comprador da Silva");
        comprador.setEmail("comprador@gmail.com");
        comprador.setSenha("#1234567890#");
        Calendar c = Calendar.getInstance();
        c.set(1984, Calendar.SEPTEMBER, 24, 0, 0, 0);
        comprador.setDataNascimento(c.getTime());

        Endereco endereco = new Endereco();
        endereco.setBairro("Várzea");
        endereco.setCep("50770-340");
        endereco.setLogradouro("Avenida Professor Moraes Rego");
        endereco.setEstado("Pernambuco");
        endereco.setNumero(40);
        endereco.setCidade("Recife");
        
        comprador.setEndereco(endereco);
        
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setBandeira("VISA");
        c.set(2025, Calendar.SEPTEMBER, 1, 0, 0, 0);
        cartaoCredito.setDataExpiracao(c.getTime());
        cartaoCredito.setNumero("4556738333387293");
        
        comprador.setCartaoCredito(cartaoCredito);
        
        em.persist(comprador);
        em.flush();
        
        assertNotNull(comprador.getId());
        assertNotNull(cartaoCredito.getId());
    }

    @Test
    public void consultarComprador() {
        Comprador comprador = em.find(Comprador.class, 2L);
        assertNotNull(comprador);
        assertEquals("740.707.044-00", comprador.getCpf());
        assertEquals("sicrano", comprador.getLogin());
        Calendar c = Calendar.getInstance();
        c.set(1973, Calendar.AUGUST, 11, 0, 0, 0);
        assertEquals(c.getTime().toString(), comprador.getDataNascimento().toString());
        assertEquals("sicrano@gmail.com", comprador.getEmail());
        
        Endereco endereco = comprador.getEndereco();
        assertNotNull(endereco);
        assertEquals("Pernambuco", endereco.getEstado());
        assertEquals("50670-210", endereco.getCep());
        assertEquals("Iputinga", endereco.getBairro());
        assertEquals("Rua Ribeirão", endereco.getLogradouro());
        
        CartaoCredito cartaoCredito = comprador.getCartaoCredito();
        assertEquals("MASTERCARD", cartaoCredito.getBandeira());
        assertEquals("5555666677778884", cartaoCredito.getNumero());
        c.set(2023, Calendar.APRIL, 10, 0, 0, 0);
        assertEquals(c.getTime().toString(), cartaoCredito.getDataExpiracao().toString());
    }
}
