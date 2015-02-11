package exemplo.jpa.test;

import exemplo.jpa.Categoria;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MASC
 */
public class JpqlTest {
    private static EntityManagerFactory emf;
    private static final Logger LOGGER = Logger.getGlobal();
    private EntityManager em;
    
    public JpqlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        LOGGER.setLevel(Level.INFO);
        emf = Persistence.createEntityManagerFactory("exemplo_11");
        DbUnitUtil.inserirDados();
    }
    
    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }
    
    @Before
    public void setUp() {
        em = emf.createEntityManager();
    }
    
    @After
    public void tearDown() {
        em.close();
    }

    @Test
    public void categoriaPorNome() {
        Query query = em.createQuery(
                "SELECT c FROM Categoria c WHERE c.nome LIKE :nome ORDER BY c.id", 
                Categoria.class);
        query.setParameter("nome", "Instrumentos%");
        List<Categoria> categorias = query.getResultList();
        
        for (Categoria categoria : categorias) {
            assertTrue(categoria.getNome().startsWith("Instrumentos"));
        }        
    }
    
    @Test
    public void categoriaPorNome2() {
        Query query = em.createNamedQuery("Categoria.PorNome", Categoria.class);
        query.setParameter("nome", "Instrumentos%");
        List<Categoria> categorias = query.getResultList();
        
        for (Categoria categoria : categorias) {
            assertTrue(categoria.getNome().startsWith("Instrumentos"));
        }        
    }
    
    @Test
    public void categoriasQuantidadeFilhas() {
        Query query = em.createQuery("SELECT COUNT(c) FROM Categoria c WHERE c.mae IS NOT NULL");
        Long resultado = (Long) query.getSingleResult();        
        assertEquals(new Long(3), resultado);
    }    

}
