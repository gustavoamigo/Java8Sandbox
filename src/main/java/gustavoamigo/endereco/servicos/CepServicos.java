package gustavoamigo.endereco.servicos;

import gustavoamigo.endereco.entitdades.Cep;
import gustavoamigo.endereco.persistencia.Persistence;

/**
 * @author Gustavo
 */
public class CepServicos {

    final private Persistence persistence;

    public CepServicos(Persistence persistence) {
        this.persistence = persistence;
    }

    public Cep find(String cep) {
        return persistence.query(em -> {
            Cep cepFound = null;
            int attempt = 0;
            while(attempt<=3 && cepFound == null) {
                String cepToFind = cep;
                if(attempt > 0) {
                    cepToFind = cep.substring(0, 8-attempt) 
                            + "000".substring(0,attempt);
                }
                cepFound = em.find(Cep.class, cepToFind);
                attempt++;
            }
            return cepFound;
        });
    }
}
