/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.sga.cliente.criteria;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mx.com.gm.sga.domain.Persona;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author DELL
 */
public class PruebaApiCriteria {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConsolaJpqlPU");
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder cb = null;
        CriteriaQuery<Persona> criteriaQuery = null;//Asociamos una entidad, la de persona
        Root<Persona> fromPersona = null;
        TypedQuery<Persona> query = null;
        Persona persona = null;

        //Distintas Pruebas con el API de Criteria.
        //consultaPersonas(em);
        //consultaWhere(em);
        //consultaDinamica(em);
    }

    private static void consultaDinamica(EntityManager em) throws RuntimeException {
        CriteriaBuilder cb;
        CriteriaQuery<Persona> criteriaQuery;
        Root<Persona> fromPersona;
        TypedQuery<Persona> query;
        Persona persona;

        // 2-b) Consulta de la persona con id pero dinamico
        //Equivalente en jpql = "select p from Persona p where p.idPersona = 1";
        log.debug("\n2-b) Consulta de la persona con id pero dinamico");
        cb = em.getCriteriaBuilder();
        criteriaQuery = cb.createQuery(Persona.class);
        fromPersona = criteriaQuery.from(Persona.class);
        criteriaQuery.select(fromPersona);
        //La Clase Predicate permite agregar varios criterios o predicados dinamicamente
        //Por emdio del objeto predicate agregamos varios objeots dinamicamente
        //O tambien llamdas restricciones (where and or)
        List<Predicate> criterios = new ArrayList<Predicate>();
        //Verificamos si tenemos criterios que agregar
        Integer idPersonaParam = null;
        idPersonaParam = 1;
        if (idPersonaParam != null) {
            //ya no lo concatenamos en el where si no que lo hacemos por sepaarado
            ParameterExpression<Integer> p = cb.parameter(Integer.class, "idPersona");
            criterios.add(cb.equal(fromPersona.get("idPersona"), p));
        }
        //Se agregan los criterios
        if (criterios.isEmpty()) {
            throw new RuntimeException("SIn criteria Query");
        } else if (criterios.size() == 1) {

            criteriaQuery.where(criterios.get(0));
        } else {
            //lo convertimo a un array y los unimos con and o puede ser con or
            criteriaQuery.where(cb.and(criterios.toArray(new Predicate[0])));
        }
        //Se crea el query con los criterios
        query = em.createQuery(criteriaQuery);
        if (idPersonaParam != null) {
            query.setParameter("idPersona", idPersonaParam);
        }
        //Ejecutamos el query
        persona = query.getSingleResult();
        log.debug(persona);
    }

    private static void consultaWhere(EntityManager em) {

        CriteriaBuilder cb;
        CriteriaQuery<Persona> criteriaQuery;
        Root<Persona> fromPersona;
        Persona persona;
        //2-a) Consulta de la persona con id = 1;
        //jpql = "select p from Persona p where p.idPersona = 1";
        log.debug("\n2-a) Consulta de la persona con id = 1");
        cb = em.getCriteriaBuilder();
        criteriaQuery = cb.createQuery(Persona.class);
        fromPersona = criteriaQuery.from(Persona.class);
        
        criteriaQuery.select(fromPersona).where(cb.equal(fromPersona.get("idPersona"), 1));
        persona = (Persona) em.createQuery(criteriaQuery).getSingleResult();
        log.debug(persona);
    }

    private static void consultaPersonas(EntityManager em) {
        CriteriaBuilder cb;
        CriteriaQuery<Persona> criteriaQuery;
        Root<Persona> fromPersona;
        TypedQuery<Persona> query;
        //Query utilizando el API de Criteria
        //1) COnsulta de todas las personas
        log.debug("\n1) Consulta de todas las personas");
        //Paso 1 El obejto Entity Manager crea instancia de Criteria Builder
        cb = em.getCriteriaBuilder();
        //Paso 2 Se crea un obejto Criteria Query
        criteriaQuery = cb.createQuery(Persona.class);
        //Paso 3 Creamos el objeto raiz del query
        fromPersona = criteriaQuery.from(Persona.class);
        //Paso 4 Seleccionamos lo necesario del from, la consulta
        criteriaQuery.select(fromPersona);
        //Paso 5 Hacemos el query TypedSafe
        query = em.createQuery(criteriaQuery);
        //Paso 6 Ejecutamos la consulta
        List<Persona> personas = query.getResultList();
        mostrarPersonas(personas);
    }

    private static void mostrarPersonas(List<Persona> personas) {
        for (Persona persona : personas) {
            log.debug(persona);
        }
    }
}
