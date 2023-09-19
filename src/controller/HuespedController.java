package controller;

import java.sql.Date;
import java.util.List;
import dao.HuespedDAO;
import modelo.Huesped;
import factory.ConnectionFactory;

public class HuespedController {

	 private final HuespedDAO huespedDAO;

	    public HuespedController() {
	        this.huespedDAO = new HuespedDAO(new ConnectionFactory().recuperarConexion());
	    }

	    public List<Huesped> listar() {
	        return huespedDAO.listar();
	    }

	    public List<Huesped> listar(String apellido) {
	        return huespedDAO.listar(apellido);
	    }

	    public void guardar(Huesped huesped, String idReserva) {
	        huesped.setIdReserva(idReserva);
	        huespedDAO.guardar(huesped);
	    }

	    public int actualizar(Integer idHuesped, String nombre, String apellido, Date fechaNacimiento,
	            String nacionalidad, String telefono) {
	        return huespedDAO.actualizar(idHuesped, nombre, apellido, fechaNacimiento, nacionalidad, telefono);
	    }

	    public int eliminar(Integer idHuesped, String idReserva) {
	        return huespedDAO.eliminar(idHuesped, idReserva);
	    }
}
