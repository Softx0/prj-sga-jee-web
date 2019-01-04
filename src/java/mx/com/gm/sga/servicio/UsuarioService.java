package mx.com.gm.sga.servicio;

import java.util.List;
import javax.ejb.Local;
import mx.com.gm.sga.domain.Usuario;

/**
 *
 * @author DELL
 */
@Local
public interface UsuarioService {

    public List<Usuario> listarUsuarios();

    public Usuario encontrarUsuarioPorId(Usuario usuario);

    public void registrarUsuario(Usuario usuario);

    public void modificarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);

}
