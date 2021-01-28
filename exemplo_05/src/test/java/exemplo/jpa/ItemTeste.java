/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masc1
 */
public class ItemTeste extends Teste {

    @Test
    public void persistirItemSemOferta() {
        Item item = new Item();
        item.setDescricao("Piano digital Roland");
        item.setTitulo("Piano digital em estado de novo"); 
        
        Categoria categoria = em.find(Categoria.class, 1L);
        item.adicionar(categoria);
        em.persist(item);
        em.flush();
        assertNotNull(item.getId());
    }
    
    @Test
    public void persistirItemComOferta() {
        Item item = new Item();
        item.setDescricao("Honda CG 160 Titan S");
        item.setTitulo("CG 160 Titan S Nova"); 
        
        Categoria meioTransporte = em.find(Categoria.class, 5L);
        Categoria motocicleta = em.find(Categoria.class, 6L);
        item.adicionar(meioTransporte);
        item.adicionar(motocicleta);
        
        Oferta oferta1 = new Oferta();
        oferta1.setValor(new Double("12000"));
        Oferta oferta2 = new Oferta();
        oferta2.setValor(new Double("11999.90"));        
        item.adicionar(oferta1);
        item.adicionar(oferta2);
        em.persist(item);
        em.flush();
        assertNotNull(item.getId());     
        assertNotNull(oferta1.getId());
        assertNotNull(oferta2.getId());
    }    
    
    @Test
    public void consultarItem() {
        Item item = em.find(Item.class, 3L);
        assertNotNull(item);
        assertEquals("Gibson SG Standard", item.getTitulo());
        assertEquals("Guitarra Gibson em estado de nova", item.getDescricao());
        
        assertEquals(2, item.getOfertas().size());
        
        item.getOfertas().forEach(oferta -> {
            assertThat(oferta.getValor().toString(),
                    CoreMatchers.anyOf(
                            startsWith("6000.0"),
                            startsWith("6300.0")));
        });        
        
        assertEquals(2, item.getCategorias().size());
        
        item.getCategorias().forEach(categoria -> {
            assertThat(categoria.getNome(),
                    CoreMatchers.anyOf(
                            startsWith("Instrumentos Musicais"),
                            startsWith("Guitarras")));
        });        
    }
}
