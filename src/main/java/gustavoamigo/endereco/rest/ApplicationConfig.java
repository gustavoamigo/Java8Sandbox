package gustavoamigo.endereco.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ServerProperties;

/**
 * @author Gustavo
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    
    @Override
    public Map getProperties() {
        Map properties = new HashMap() ;
        properties.put(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        
        return properties;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(gustavoamigo.endereco.rest.CepsRest.class);
        resources.add(gustavoamigo.endereco.rest.EnderecosRest.class);
    }
    
}
