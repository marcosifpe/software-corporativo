package exemplo.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteInsert {

    public static void main(String[] args) {
        //EntityManagerFactory realiza a leitura das informações relativas à "persistence-unit".
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exemplo_01");
        //Criação do EntityManager
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin(); //Início da transação
        Usuario usuario = new Usuario();
        usuario.setNome("Marcos Costa");
        usuario.setEmail("marcoscosta@recife.ifpe.edu.br");
        usuario.setLogin("marcoscosta");
        usuario.setSenha("teste");
        em.persist(usuario);
        em.getTransaction().commit(); //Commit da transação
        em.close();
        factory.close();
    }
}
