package exemplo.jpa;

import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TesteJPA {

    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        preencherUsuario(usuario);
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            //EntityManagerFactory realiza a leitura das informações relativas à "persistence-unit".
            emf = Persistence.createEntityManagerFactory("exemplo_02");
            em = emf.createEntityManager(); //Criação do EntityManager.
            et = em.getTransaction(); //Recupera objeto responsável pelo gerenciamento de transação.
            et.begin();
            em.persist(usuario);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive())
                et.rollback();
        } finally {
            if (em != null)
                em.close();       
            if (emf != null)
                emf.close();
        }
    }

    private static void preencherUsuario(Usuario usuario) {
        usuario.setNome("Fulano da Silva");
        usuario.setEmail("fulano@gmail.com");
        usuario.setLogin("fulano");
        usuario.setSenha("teste");
        usuario.setCpf("534.585.764-49");     
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        usuario.setDataNascimento(c.getTime());
    }
}
