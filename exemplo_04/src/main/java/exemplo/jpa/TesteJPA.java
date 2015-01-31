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
            emf = Persistence.createEntityManagerFactory("exemplo_04");
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(usuario);
            et.commit();
            
            em.clear(); //Força a consulta ao banco de dados, já que a classe que acabou de ser persistida está na cache.
            UsuarioId usuarioId = new UsuarioId();
            usuarioId.setCpf("534.585.764-49");
            usuarioId.setLogin("fulano");
            usuario = em.find(Usuario.class, usuarioId);
            System.out.println(usuario);

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
