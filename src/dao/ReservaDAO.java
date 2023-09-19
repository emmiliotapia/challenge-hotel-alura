package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Reserva;

public class ReservaDAO {

	private Connection con;

    public ReservaDAO(Connection con) {
        this.con = con;
    }

    public List<Reserva> listar() {
        List<Reserva> listarReservas = new ArrayList<>();
        try {
            String sql = "SELECT id_reserva, fecha_entrada, fecha_salida, valor, forma_pago "
                    + "FROM reservas";
            try ( PreparedStatement preparedStatement = con.prepareStatement(sql);) {
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    Reserva fila = new Reserva(
                            resultSet.getString("ID_RESERVA"),
                            resultSet.getDate("FECHA_ENTRADA"),
                            resultSet.getDate("FECHA_SALIDA"),
                            resultSet.getBigDecimal("VALOR"),
                            resultSet.getString("FORMA_PAGO")
                    );
                    listarReservas.add(fila);
                }
                return listarReservas;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reserva> listar(String idReserva) {
        List<Reserva> listaReservas = new ArrayList<>();
        String sql = "SELECT\n"
                + "id_reserva, fecha_entrada, fecha_salida, valor, forma_pago\n"
                + "FROM reservas\n"
                + "WHERE id_reserva LIKE ?";
        try {
            try ( PreparedStatement preparedStatement = con.prepareStatement(sql);) {
                preparedStatement.setString(1, idReserva.concat("%"));
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    Reserva fila = new Reserva(
                            resultSet.getString("ID_RESERVA"),
                            resultSet.getDate("FECHA_ENTRADA"),
                            resultSet.getDate("FECHA_SALIDA"),
                            resultSet.getBigDecimal("VALOR"),
                            resultSet.getString("FORMA_PAGO")
                    );
                    listaReservas.add(fila);
                }
                return listaReservas;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardar(Reserva reserva) {
        try {
            String sql = "INSERT INTO reservas (id_reserva, fecha_entrada, fecha_salida, valor, forma_pago) "
                    + "VALUES (?, ?, ?, ?, ?)";
            try ( PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                preparedStatement.setString(1, reserva.getId_Reserva());
                preparedStatement.setDate(2, reserva.getFechaEntrada());
                preparedStatement.setDate(3, reserva.getFechaSalida());
                preparedStatement.setBigDecimal(4, reserva.getValorReserva());
                preparedStatement.setString(5, reserva.getFormaPago());
                preparedStatement.execute();
                try ( ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                    while (resultSet.next()) {
                        System.out.println(
                                String.format("Fue insertada la reserva: %s", reserva)
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int actualizar(String idReserva, Date fechaEntrada,
            Date fechaSalida, double valorReserva, String formaPago) {
        try {
            String sql = "UPDATE reservas "
                    + "SET fecha_entrada = ?, fecha_salida = ?, valor = ?, forma_pago = ? "
                    + "WHERE id_reserva = ?";
            try ( PreparedStatement preparedStatement = con.prepareStatement(sql);) {
                preparedStatement.setDate(1, fechaEntrada);
                preparedStatement.setDate(2, fechaSalida);
                preparedStatement.setDouble(3, valorReserva);
                preparedStatement.setString(4, formaPago);
                preparedStatement.setString(5, idReserva);
                preparedStatement.execute();
                int updateCount = preparedStatement.getUpdateCount();
                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
