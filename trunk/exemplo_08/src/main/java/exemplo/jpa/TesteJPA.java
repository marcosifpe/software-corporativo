package exemplo.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TesteJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo_08");
    private static final Logger logger = Logger.getGlobal();

    static {
        logger.setLevel(Level.INFO);
    }
    
    public static void main(String[] args) {
        try {
            criarCategorias();
            criarItens();
        } finally {
            emf.close();
        }
    }
    
    public static void criarCategorias() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Categoria categoria1 = new Categoria();
        categoria1.setNome("Informática");
        Categoria categoria2 = new Categoria();
        categoria2.setNome("Hardware");
        Categoria categoria3 = new Categoria();
        categoria3.setNome("Software");
        Categoria categoria4 = new Categoria();
        categoria4.setNome("Equipamentos Musicais");
        
        try {
            et.begin();
            em.persist(categoria1);
            em.persist(categoria2);
            em.persist(categoria3);
            em.persist(categoria4);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                Logger.getGlobal().log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                Logger.getGlobal().info("Transação cancelada.");
            }
        } finally {
            em.close();
        }

    }

    private static void criarItens() {
        
    }
}
