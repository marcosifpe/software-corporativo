package exemplo.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TesteInsert {

    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        preencherUsuario(usuario);
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        System.out.println("aqui");
        try {
            //EntityManagerFactory realiza a leitura das informações relativas à "persistence-unit".
            emf = Persistence.createEntityManagerFactory("exemplo_01");
            em = emf.createEntityManager(); //Criação do EntityManager.
            et = em.getTransaction();
            et.begin();
            em.persist(usuario);
            et.commit();
        } catch (Exception ex) {
            if (et != null)
                et.rollback();
        } finally {
            if (em != null)
                em.close();       
            if (emf != null)
                emf.close();
        }
    }

    private static void preencherUsuario(Usuario usuario) {
        usuario.setNome("Marcos Costa");
        usuario.setEmail("marcoscosta@recife.ifpe.edu.br");
        usuario.setLogin("marcoscosta");
        usuario.setSenha("teste");
    }
}
