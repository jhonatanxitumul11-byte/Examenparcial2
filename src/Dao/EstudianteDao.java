package Dao;

import Conexion.CreateConnection;
import Modelo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDao {
    private final CreateConnection connFactory = new CreateConnection();

    public List<Estudiante> obtenerTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM Estudiante ORDER BY idEstudiante";

        try (Connection con = connFactory.getConection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(convertir(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Estudiante obtenerPorId(int idEstudiante) {
        String sql = "SELECT * FROM Estudiante WHERE idEstudiante = ?";

        try (Connection con = connFactory.getConection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return convertir(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean guardar(Estudiante p) {
        String sql = "INSERT INTO Estudiante(Carnet, FirstName, SecondName, LastName, Direccion, Telefono, Carrera, FechaNacimiento, FechaIngreso, CuotaMensual) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = connFactory.getConection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getCarnet());
            ps.setString(2, p.getFirstName());
            ps.setString(3, p.getSecondName());
            ps.setString(4, p.getLastName());
            ps.setString(5, p.getDireccion());
            ps.setInt(6, p.getTelefono());
            ps.setString(7, p.getCarrera());
            ps.setString(8, p.getFechaNacimiento());
            ps.setString(9, p.getFechaIngreso());
            ps.setDouble(10, p.getCuotaMensual());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Estudiante p) {
        String sql = "UPDATE Estudiante SET Carnet=?, FirstName=?, SecondName=?, LastName=?, Direccion=?, Telefono=?, Carrera=?, FechaNacimiento=?, FechaIngreso=?, CuotaMensual=? WHERE idEstudiante=?";

        try (Connection con = connFactory.getConection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getCarnet());
            ps.setString(2, p.getFirstName());
            ps.setString(3, p.getSecondName());
            ps.setString(4, p.getLastName());
            ps.setString(5, p.getDireccion());
            ps.setInt(6, p.getTelefono());
            ps.setString(7, p.getCarrera());
            ps.setString(8, p.getFechaNacimiento());
            ps.setString(9, p.getFechaIngreso());
            ps.setDouble(10, p.getCuotaMensual());
            ps.setInt(11, p.getIdEstudiante());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idEstudiante) {
        String sql = "DELETE FROM Estudiante WHERE idEstudiante=?";

        try (Connection con = connFactory.getConection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Estudiante convertir(ResultSet rs) throws SQLException {
        return new Estudiante(
            rs.getInt("idestudiante"),
            rs.getString("carnet"),
            rs.getString("firstname"),
            rs.getString("secondname"),
            rs.getString("lastname"),
            rs.getString("direccion"),
            rs.getInt("telefono"),
            rs.getString("carrera"),
            rs.getString("fechanacimiento"),
            rs.getString("fechaingreso"),
            rs.getDouble("cuotamensual")
        );
    }
}
