/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.sga.cliente.cascada;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import mx.com.gm.sga.domain.Persona;
import mx.com.gm.sga.domain.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersistenciaCascadaJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConsolaJpqlPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        //Inicia la transaccion

        //Paso 1. Crea nuevo objeto
        //Objeto en estado transitivo
        Persona persona1 = new Persona("Hugo", "Sanchez", "Pinto", "hsanchez@mail.com", "33334444");

        //Creamos el objeto usuario (tiene dependencia con un objeto persona)
        Usuario usuario1 = new Usuario("hsanchez", "123", persona1);

        //Paso 2. Inicia transaccion
        tx.begin();

        //Paso 3. Ejecuta SQL
        //Solo persistimos el objeto Usuario
        //No hay necesidad de persistir el objeto persona, lo harÃ¡ por cascadeo
        em.persist(usuario1);

        //Paso 4. Commit/rollback
        tx.commit();

        //Objeto en estado detached
        log.debug("Objeto persistido Usuario:" + usuario1);
        log.debug("Objeto persistido Persona:" + persona1);

        //Cerramos el Entity Manager
        em.close();
    }
}
