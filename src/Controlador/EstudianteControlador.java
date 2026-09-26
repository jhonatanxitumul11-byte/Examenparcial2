package Controlador;

import Dao.EstudianteDao;
import Modelo.Estudiante;
import java.util.List;

public class EstudianteControlador {
    private final EstudianteDao dao = new EstudianteDao();

    public boolean guardarEstudiante(Estudiante p) {
        return dao.guardar(p);
    }

    public Estudiante consultarEstudiante(int idEstudiante) {
        return dao.obtenerPorId(idEstudiante);
    }

    public List<Estudiante> obtenerEstudiantes() {
        return dao.obtenerTodos();
    }

    public boolean actualizarEstudiante(Estudiante p) {
        return dao.actualizar(p);
    }

    public boolean eliminarEstudiante(int idEstudiante) {
        return dao.eliminar(idEstudiante);
    }
}
