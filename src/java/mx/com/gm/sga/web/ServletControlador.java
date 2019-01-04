package mx.com.gm.sga.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.com.gm.sga.domain.Persona;
import mx.com.gm.sga.servicio.PersonaService;

/**
 *
 * @author DELL
 */
@WebServlet({"/ServletControlador", "/ListarPersonas"})
//@WebServlet("/ListarPersonas")
public class ServletControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private PersonaService personaService;

   
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
       // processRequest(request, response);
        String accion = request.getParameter("accion");

        if (accion != null && accion.equals("editar")) {

            //1. Recuperamos el idPersona seleccionado
            String idPersonaString = request.getParameter("idPersona");
            int idPersona = 0;

            if (idPersonaString != null) {

                //2. Creamos el objeto persona a recuperar
                idPersona = Integer.parseInt(idPersonaString);
                Persona persona = new Persona(idPersona);
                persona = this.personaService.encontrarPersonaPorId(persona);

                //3. Compartimos el objeto persona en alcance request
                request.setAttribute("persona", persona);

                //4. Redireccionamos a la pagina para editar el objeto Persona
                request.getRequestDispatcher("editarPersona.jsp")
                        .forward(request, response);

            }

        } else {
            //La accion por default es listar
            //4. Listamos las personas
            this.listarPersonas(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        //processRequest(request, response);
        
        String accion = request.getParameter("accion");
        if (accion != null && accion.equals("agregar")) {

            //1. Recuperamos los parametros
            String nombre = request.getParameter("nombre");
            String apePaterno = request.getParameter("apePaterno");
            String email = request.getParameter("email");
            //String telefono = request.getParameter("telefono");

            //2. Creamos el objeto Persona
            Persona persona = new Persona();
            persona.setNombre(nombre);
            persona.setApellidoPaterno(apePaterno);
            persona.setEmail(email);
            //persona.setTelefono(telefono);

            try {
                //3. Utilizamos la capa de servicio para agregar la persona
                //Si ya existe el email no deberia registrarse
                this.personaService.registrarPersona(persona);
            } catch (Exception e) {
                //Informamos cualquier error
                e.printStackTrace(System.out);
            }

            //4. Listamos las personas
            this.listarPersonas(request, response);

        } else if (accion != null && accion.equals("modificar")) {
            //Dentro de la modificacion, se puede modificar o eliminar segun se haya seleccionado

            //Verificamos si se presiono el boton de guardar
            String botonGuardar = request.getParameter("guardar");
            String botonEliminar = request.getParameter("eliminar");

            if (botonGuardar != null) {

                //1. Recuperamos los parametros
                String idPersonaString = request.getParameter("idPersona");
                String nombre = request.getParameter("nombre");
                String apePaterno = request.getParameter("apePaterno");
                String email = request.getParameter("email");
                //String telefono = request.getParameter("telefono");

                //2. Creamos el objeto Persona
                Persona persona = new Persona();
                int idPersona = Integer.parseInt(idPersonaString);
                persona.setIdPersona(idPersona);
                persona.setNombre(nombre);
                persona.setApellidoPaterno(apePaterno);
                persona.setEmail(email);
                //persona.setTelefono(telefono);

                try {
                    //3. Utilizamos la capa de servicio para modificar la persona
                    this.personaService.modificarPersona(persona);
                } catch (Exception e) {
                    //Informamos cualquier error
                    e.printStackTrace(System.out);
                }

                //4. Listamos las personas
                this.listarPersonas(request, response);

            } else if (botonEliminar != null) {
                //1. Recuperamos los parametros
                String idPersonaString = request.getParameter("idPersona");

                //2. Creamos el objeto Persona
                int idPersona = Integer.parseInt(idPersonaString);
                Persona persona = new Persona(idPersona);

                try {
                    //3. Eliminamos a la persona
                    this.personaService.eliminarPersona(persona);
                    
                } catch (Exception e) {
                    //Informamos cualquier error
                    e.printStackTrace(System.out);
                }

                //4. Listamos las personas
                this.listarPersonas(request, response);

            }

        } else {
            //Accion por default, listamos las personas
            this.listarPersonas(request, response);
        }
    }

    private void listarPersonas(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        //4. Redireccionamos cargando nuevamente el listado
        List<Persona> personas = personaService.listarPersonas();
        request.setAttribute("personas", personas);
        request.getRequestDispatcher("listarPersonas.jsp").forward(request, response);
    }
}
