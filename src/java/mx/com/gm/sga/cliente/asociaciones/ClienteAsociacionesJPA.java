/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.sga.cliente.asociaciones;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import mx.com.gm.sga.domain.Persona;
import mx.com.gm.sga.domain.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClienteAsociacionesJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConsolaJpqlPU");
        EntityManager em = emf.createEntityManager();

        List<Persona> personas = em.createNamedQuery("Persona.findAll").getResultList();

        //Cerramos la conexion
        em.close();

        //Imprimimos las personas
        imprimirPersonas(personas);
    }

    private static void imprimirPersonas(List<Persona> personas) {
        
        //Objetos en estado detached o desconectados
        for (Persona persona : personas) {
            
            log.debug("Persona: " + persona);
            //Recuperamos los usuarios de cada persona
            //Claro esta debemos de poner la anotaciones EAGER en la entidad de usuarios
            for (Usuario usuario : persona.getUsuarios()) {
                
                log.debug("Usuario: " + usuario);
                
            }
        }
    }
}
