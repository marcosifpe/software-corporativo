package exemplo.jpa.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    public void hello() {
        assertTrue(true);
    }
}
