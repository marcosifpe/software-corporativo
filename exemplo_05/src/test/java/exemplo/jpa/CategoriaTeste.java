/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masc1
 */
public class CategoriaTeste extends Teste {

    @Test
    public void persistirCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNome("Bicicletas");
        
        Categoria categoriaMae = em.find(Categoria.class, 5L);
        assertEquals("Meios de Transporte", categoriaMae.getNome());
        categoria.setMae(categoriaMae);
        em.persist(categoria);
        em.flush();
        assertNotNull(categoria.getId());
    }
    
    @Test
    public void consultarCategoria() {
        Categoria categoria = em.find(Categoria.class, 2L);
        assertEquals("Guitarras", categoria.getNome());
        Categoria categoriaMae = categoria.getMae();
        assertNotNull(categoriaMae);
        assertEquals("Instrumentos Musicais", categoriaMae.getNome());
    }
}
