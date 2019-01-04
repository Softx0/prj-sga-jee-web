/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.sga.cliente.ciclovida;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import mx.com.gm.sga.domain.Persona;
import mx.com.gm.sga.domain.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EliminarObjetoJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConsolaJpqlPU");
        EntityManager em = emf.createEntityManager();

        // Paso 1. Inicia transaccion 1
        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();

        // Paso 2. Ejecuta SQL de tipo select
        //Proporcionamos un id_persona valido
        Usuario persona1 = em.find(Usuario.class, 1);

        // Paso 3. Termina transaccion 1
        tx1.commit();

        // Objeto en estado detached
        log.debug("Objeto encontrado:" + persona1);

        // Paso 4. Incia transaccion 2
        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();

        // Paso 5. Ejecuta SQL (es un delete)
        em.remove(persona1);

        // Paso 6. Termina transaccion 2
        // Al momento de hacer commit, 
        //se realiza el delete
        tx2.commit();

        // Objeto en estado detached ya modificado
        //Ya no es posible resincronizarlo en otra transaccion
        //Solo esta en memoria, pero al terminar el metodo se eliminara
        log.debug("Objeto eliminado:" + persona1);
    }
}
