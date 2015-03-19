package gustavoamigo.endereco.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.*;

/**
 * @author Gustavo
 */
public class Persistence {

    /**
     * Initialization on demand holder idiom
     * https://en.wikipedia.org/wiki/Singleton_pattern#Initialization-on-demand_holder_idiom
     * 
     * Initializes singleton.
     *
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
     * not before.
     */
    private static class SingletonHolder {

        private static final Persistence INSTANCE = new Persistence();
    }

    private EntityManagerFactory emf;

    private Persistence() {
        this.emf = javax.persistence.Persistence.createEntityManagerFactory("enderecopu");
    }

    public static Persistence getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void command(Consumer<EntityManager> comm) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            comm.accept(em);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public <T> T query(Function<EntityManager, T> query) {
        EntityManager em = emf.createEntityManager();
        T returnValue;
        try {
            returnValue = query.apply(em);
        } finally {
            em.close();
        }
        return returnValue;
    }

}
