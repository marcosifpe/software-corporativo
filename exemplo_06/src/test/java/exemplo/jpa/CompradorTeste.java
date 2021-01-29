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
        
        CartaoCredito cartaoCredito = comprador.getCartaoCredito();
        assertEquals("MASTERCARD", cartaoCredito.getBandeira());
        assertEquals("5555666677778884", cartaoCredito.getNumero());
        c.set(2023, Calendar.APRIL, 10, 0, 0, 0);
        assertEquals(c.getTime().toString(), cartaoCredito.getDataExpiracao().toString());
    }
}
