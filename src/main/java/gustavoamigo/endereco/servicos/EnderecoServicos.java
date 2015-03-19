package gustavoamigo.endereco.servicos;

import gustavoamigo.endereco.entitdades.Cep;
import gustavoamigo.endereco.entitdades.Endereco;
import gustavoamigo.endereco.persistencia.Persistence;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Gustavo
 */
public class EnderecoServicos {
    
    final private Persistence persistence;
    private CepServicos cepServices;
    
    public EnderecoServicos(Persistence persistence, CepServicos cepServices) {
        this.persistence = persistence;
        this.cepServices = cepServices;
    }
    
    public void create(Endereco entity) throws CepNotFoundException {
        Cep cep = cepServices.find(entity.getCep());
        if(cep == null) throw new CepNotFoundException("Cep não encontrado");
        persistence.command(em -> {
           em.persist(entity); 
        });
    }
    
    public void edit(Long id, Endereco entity) throws CepNotFoundException {
        Cep cep = cepServices.find(entity.getCep());
        if(cep == null) throw new CepNotFoundException("Cep não encontrado");
        entity.setId(id);
        persistence.command(em -> {
            em.merge(entity);
        });
    }
    
    public void remove(Long id) {
        persistence.command(em -> {
            em.remove(em.find(Endereco.class, id));
        });
    }
    
    public Endereco find(Long id) {
       return persistence.query(em -> {
            return em.find(Endereco.class, id);
        });
    }

    public List<Endereco> findAll() {
        return persistence.query(em -> {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
            return em.createQuery(cq).getResultList();
        });
    }
}
