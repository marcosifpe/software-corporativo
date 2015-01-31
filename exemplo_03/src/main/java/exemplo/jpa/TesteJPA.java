package exemplo.jpa;

import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TesteJPA {

    public static void main(String[] args) {
        Usuario usuario1 = new Usuario();
        preencherUsuario(usuario1, "761.677.347-80", "FULANO");
        Usuario usuario2 = new Usuario();
        preencherUsuario(usuario2, "533.966.206-31", "SICRANO");
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et;
        try {
            //EntityManagerFactory realiza a leitura das informações relativas à "persistence-unit".
            emf = Persistence.createEntityManagerFactory("exemplo_03");
            em = emf.createEntityManager(); //Criação do EntityManager.
            et = em.getTransaction(); //Recupera objeto responsável pelo gerenciamento de transação.
            et.begin();
            em.persist(usuario1);
            em.persist(usuario2);
            et.commit();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    private static void preencherUsuario(Usuario usuario, String cpf, String login) {
        usuario.setNome(login + " DA SILVA");
        usuario.setEmail(login + "@gmail.com");
        usuario.setLogin(login);
        usuario.setSenha("TESTE" + login);
        usuario.setCpf(cpf);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        usuario.setDataNascimento(c.getTime());
    }
}
