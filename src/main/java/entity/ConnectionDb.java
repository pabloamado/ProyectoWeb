package entity;

import java.sql.*;
import java.util.*;

public class ConnectionDb {

	private static Connection connection;
	private static ConnectionDb connectionDb;

	private ConnectionDb() {

		initConnection();

	}

	public void initConnection() {

		try {

			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(ConnectionConfig.CONNECTION_URL(), ConnectionConfig.USER(),
					ConnectionConfig.PASSWORD());

		} catch (SQLException e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();

		} catch (ClassNotFoundException ex) {

			ex.printStackTrace(ExceptionWriter.getStream());
			ex.printStackTrace();
		}

	}

// CIERRA LA CONEXION
	private void closeConnection() {

		try {

			connection.setAutoCommit(true);
			connection.close();

		} catch (SQLException e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();

		}

	}
	
	//REVISA SI LA CONEXION ESTA CERRADA, SI LO ESTA LA ABRE.
	private void checkConnection() {
		
		try {
			
			if (connection.isClosed()) {

				initConnection();

			}
			
		} catch (SQLException e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}
		
	}

	public static ConnectionDb getConnectionDb() {
		
		if (connectionDb == null) {

			connectionDb = new ConnectionDb();

		}

		return connectionDb;
	}

	//REALIZA UN INSERT EN LA DB
	public boolean saveData(String query, HashMap<Integer, Object> map) {

		boolean success = false;
		PreparedStatement preparedStatement = null;

		try {

			checkConnection();
			
			connection.setAutoCommit(false);

			preparedStatement = getPreparedStatement(query, map);

			preparedStatement.executeUpdate();

			connection.commit();

			preparedStatement.close();

			success = true;

		} catch (SQLException e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();

			doRollBack();

		} finally {

			closeConnection();

		}

		return success;
	}

	//REALIZA UN SELECT EN LA DB
	public List<HashMap<String, Object>> getEntity(String query, HashMap<Integer, Object> map) {

		List<HashMap<String, Object>> registerList = null;

		try {

			checkConnection();

			PreparedStatement preparedStatement = getPreparedStatement(query, map);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData metadata = resultSet.getMetaData();

			registerList = getDataList(resultSet, metadata);

			resultSet.close();

			preparedStatement.close();

		} catch (SQLException e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();

		} finally {

			closeConnection();

		}

		return registerList;
	}

	// REALIZA UN SELECT PARA POSTEOS Y COMENTARIOS 
	public int getIdGenerated(String query, HashMap<Integer, Object> map) {

		int idGenerated = 0;
		PreparedStatement preparedStatement = null;

		try {

			checkConnection();

			connection.setAutoCommit(false);

			preparedStatement = getPreparedStatement(query, map);

			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {

				idGenerated = resultSet.getInt(1);

			}

			connection.commit();

			preparedStatement.close();

		} catch (SQLException e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();

			doRollBack();

		} finally {

			closeConnection();

		}

		return idGenerated;
	}

	//REALIZA UN DELETE EN CASCADA
	public boolean executeTransaction(String query1, String query2, HashMap<Integer, Object> map1,
			HashMap<Integer, Object> map2) {

		boolean success = false;
		PreparedStatement deleteComments = null;
		PreparedStatement deletePost = null;

		try {

			checkConnection();

			connection.setAutoCommit(false);
			deleteComments = getPreparedStatement(query1, map1);
			deletePost = getPreparedStatement(query2, map2);
			deleteComments.executeUpdate();
			deletePost.executeUpdate();
			connection.commit();
			success = true;

			deleteComments.close();
			deletePost.close();

		} catch (SQLException e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();

			doRollBack();

		} finally { // SI FALLA ALGO DEBEMOS CERRAR LA CONEXION PERO PRIMERO CERRAR LOS
					// PREPAREDSTATEMENT

			closeStatement(deleteComments);
			closeStatement(deletePost);

			closeConnection();

		}

		return success;

	}

	//PREPARA EL STATEMENT CON LOS VALORES Y LLAVES  DEL MAPA PARA PERSISTIR LOS DATOS
	private PreparedStatement getPreparedStatement(String query, HashMap<Integer, Object> map) 
			throws SQLException {

		PreparedStatement preparedStatement = connection.prepareStatement(query, 
				Statement.RETURN_GENERATED_KEYS);

		if (map != null) {

			Set<Integer> keys = map.keySet();

			for (Integer key : keys) {

				Object value = map.get(key);
				preparedStatement.setObject(key, value);// columna/valor X // es la tabla donde se setea el objeto
			}

		}

		return preparedStatement;
	}
// SE ENCARGA DE RECUPERAR LOS DATOS DE LOS RESULT SET INSERTANDOLOS EN UN MAPA
	private List<HashMap<String, Object>> getDataList(ResultSet resultSet, ResultSetMetaData metaData)
			throws SQLException {

		List<HashMap<String, Object>> registerList = new ArrayList<>();

		while (resultSet.next()) {

			HashMap<String, Object> registerMap = new HashMap<>();// es un mapa para un registro

			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				// recorre cada columna y recupera el nombre de columna y el valor
				// correspondiente del registro asociado
				registerMap.put(metaData.getColumnName(i), resultSet.getObject(i));
			}

			registerList.add(registerMap);
		}

		return registerList;

	}

	// REALIZA UN RETROCESO EN LA DB
	private void doRollBack() {

		try {

			connection.rollback();

		} catch (SQLException ex) {

			ex.printStackTrace(ExceptionWriter.getStream());
			ex.printStackTrace();
		}

	}

	// CIERRA LOS PREPAREDSTATEMENT
	private void closeStatement(PreparedStatement statement) {

		if (statement != null) {

			try {

				statement.close();

			} catch (SQLException ex) {

				ex.printStackTrace(ExceptionWriter.getStream());
				ex.printStackTrace();

			}
		}

	}

}
