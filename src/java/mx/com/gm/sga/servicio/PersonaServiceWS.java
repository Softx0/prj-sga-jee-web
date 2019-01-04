/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.sga.servicio;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import mx.com.gm.sga.domain.Persona;

/**
 *
 * @author DELL
 */
@WebService
public interface PersonaServiceWS {

    @WebMethod
    public List<Persona> listarPersonas();

    @WebMethod
    public void registrarPersona(Persona persona);

    @WebMethod
    public void modificarPersona(Persona persona);

    @WebMethod
    public void eliminarPersona(Persona persona);
}
