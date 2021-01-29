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
public class VendedorTeste extends Teste {

    @Test
    public void persistirVendedor() {
        Vendedor vendedor = new Vendedor();
        vendedor.setCpf("248.008.000-56");
        vendedor.setLogin("vendedor1");
        vendedor.setNome("Vendedor da Silva");
        vendedor.setEmail("vendedor2000@gmail.com");
        vendedor.setSenha("#987654321#");
        Calendar c = Calendar.getInstance();
        c.set(1991, Calendar.OCTOBER, 12, 0, 0, 0);
        vendedor.setDataNascimento(c.getTime());
        vendedor.setReputacao("Top");
        vendedor.setValorVendas(Double.valueOf(1500000));

        Endereco endereco = new Endereco();
        endereco.setBairro("Ipsep");
        endereco.setCep("50770-680");
        endereco.setLogradouro("Avenida das Garças");
        endereco.setEstado("Pernambuco");
        endereco.setNumero(400);
        endereco.setCidade("Recife");
        vendedor.setEndereco(endereco);

        em.persist(vendedor);
        em.flush();

        assertNotNull(vendedor.getId());
    }

    @Test
    public void consultarVendedor() {
        Vendedor vendedor = em.find(Vendedor.class, 5L);
        assertNotNull(vendedor);
        assertEquals("484.854.847-03", vendedor.getCpf());
        assertEquals("v1silva", vendedor.getLogin());
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.NOVEMBER, 23, 0, 0, 0);
        assertEquals(c.getTime().toString(), vendedor.getDataNascimento().toString());
        assertEquals("vendedor1@gmail.com", vendedor.getEmail());
        assertEquals(new Double("10500.50"), vendedor.getValorVendas());
        assertEquals("EXPERIENTE", vendedor.getReputacao());

        Endereco endereco = vendedor.getEndereco();
        assertNotNull(endereco);
        assertEquals("Pernambuco", endereco.getEstado());
        assertEquals("50670-210", endereco.getCep());
        assertEquals("Iputinga", endereco.getBairro());
        assertEquals("Rua Ribeirão", endereco.getLogradouro());
    }

}
