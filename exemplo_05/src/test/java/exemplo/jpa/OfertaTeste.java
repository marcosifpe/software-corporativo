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
public class OfertaTeste extends Teste {

    @Test
    public void persitirOferta() {
        Oferta oferta = new Oferta();
        oferta.setValor(new Double("900.00"));
        Item item = em.find(Item.class, 2L);
        assertNotNull(item);
        item.adicionar(oferta);
        em.persist(oferta);
        em.flush();
        assertNotNull(oferta.getId());        
    }
    
    @Test
    public void consultarOferta() {
        Oferta oferta = em.find(Oferta.class, 2L);
        assertNotNull(oferta);
        assertEquals(new Double(520), oferta.getValor());
        
        Calendar c = Calendar.getInstance();
        c.set(2020, Calendar.JANUARY, 12, 1, 30, 52);
        assertEquals(c.getTime().toString(), oferta.getData().toString());
        
        Item item = em.find(Item.class, 1L);
        assertNotNull(item);
        assertEquals(item, oferta.getItem());
    }
}
