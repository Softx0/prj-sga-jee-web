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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActualizarObjetoJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConsolaJpqlPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //Paso 1. Inicia transaccion 1
        tx.begin();

        //Paso 2. Ejecuta SQL de tipo select
        //El id prporcionado debe existir en la base de datos
        Persona persona1 = em.find(Persona.class, 1);

        //Paso 3. Termina transaccion 1
        tx.commit();

        //Objeto en estado detached
        log.debug("Objeto recuperado:" + persona1);

        //Paso 4. setValue (nuevoValor)
        persona1.setApellidoMaterno("Nava");

        //Paso 5. Incia transaccion 2
        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();

        //Paso 6. Ejecuta SQL. Es un select, pero al estar modificado, 
        //al terminar la transaccion hara un update
        //Como ya tenemos el objeto hacemos solo un merge para resincronizar
        //el objeto a hacer merge, debe contar con el valor de la llave primaria
        em.merge(persona1);

        //Paso 7. Termina transaccion 2
        //Al momento de hacer commit, se revisan las diferencias 
        //entre el objeto de la base de datos
        //y el objeto en memoria, y se aplican los cambios si los hubiese
        tx2.commit();

        //Objeto en estado detached ya modificado
        log.debug("Objeto recuperado:" + persona1);
    }
}
