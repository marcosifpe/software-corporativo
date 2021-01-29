/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import exemplo.jpa.CartaoCredito;
import exemplo.jpa.Comprador;
import exemplo.jpa.Endereco;
import exemplo.jpa.Usuario;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcos
 */
public class CompradorCrudTest extends GenericTest {
    @Test
    public void persistirComprador() {
        logger.info("Executando persistirComprador()");
        Comprador comprador = criarComprador();
        em.persist(comprador);
        em.flush();
        assertNotNull(comprador.getId());
        assertNotNull(comprador.getCartaoCredito().getId());
        
    }
        
    @Test
    public void atualizarComprador() {
        logger.info("Executando atualizarComprador()");
        String novoEmail = "fulano_de_tal@gmail.com";
        String telefone = "(81) 990901010";
        Long id = 1L;
        Comprador comprador = em.find(Comprador.class, id);
        comprador.setEmail(novoEmail);
        comprador.addTelefone(telefone);
        em.flush();
        String jpql = "SELECT c FROM Comprador c WHERE c.id = ?1";
        TypedQuery<Comprador> query = em.createQuery(jpql, Comprador.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        comprador = query.getSingleResult();
        assertEquals(novoEmail, comprador.getEmail());        
        assertTrue(comprador.possui(telefone));
    }
    
    @Test
    public void atualizarCompradorMerge() {
        logger.info("Executando atualizarCompradorMerge()");
        String novoEmail = "fulano_de_tal2@gmail.com";
        String telefone = "(81) 990901010";
        Long id = 1L;
        Comprador comprador = em.find(Comprador.class, id);
        comprador.setEmail(novoEmail);
        comprador.addTelefone(telefone);
        em.clear();
        em.merge(comprador);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        comprador = em.find(Comprador.class, id, properties);
        assertEquals(novoEmail, comprador.getEmail());        
        assertTrue(comprador.possui(telefone));
    }    
    
    @Test
    public void removerComprador() {
        logger.info("Executando removerComprador()");
        Comprador comprador = em.find(Comprador.class, 9L);
        em.remove(comprador);
        Usuario usuario = em.find(Usuario.class, 9L);
        assertNull(usuario);
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
