/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.sga.cliente.transacciones;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import mx.com.gm.sga.domain.Persona;
import mx.com.gm.sga.servicio.PersonaServiceRemote;

/**
 *
 * @author DELL
 */
public class PruebaManejoTransacciones {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws NamingException {
        Context jndi = new InitialContext();
        PersonaServiceRemote personaService = (PersonaServiceRemote) jndi.lookup("java:global/sga-jee-web/PersonaServiceImpl!mx.com.gm.sga.servicio.PersonaServiceRemote");

        log.debug("Iniciando prueba Manejo Transaccional PersonaService");

        //Buscamos el objeto persona
        Persona persona1 = personaService.encontrarPersonaPorId(new Persona(1)); //Teniendo claro un constructor solo con el Id

        //Cambiamos la persona
        //persona1.setApellidoMaterno("Cambio con error...........................................................................................");
        persona1.setApellidoMaterno("cambio sin error");

        personaService.modificarPersona(persona1);
        log.debug("Objeto Persona Modificado: "+persona1);
        log.debug("FIn de Prueba EJB PersonaService");

    }
}
