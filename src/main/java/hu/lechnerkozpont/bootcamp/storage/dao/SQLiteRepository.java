package hu.lechnerkozpont.bootcamp.storage.dao;

import hu.lechnerkozpont.bootcamp.storage.entity.StoreItem;
import java.sql.*;


public class SQLiteRepository implements StoreItemRepository {

	Connection conn;
	private String SQL_TABLE = "product";
	private String KEY_NAME = "name";
	private String KEY_QUANTITY = "quantity";
	private String dbURL = "jdbc:sqlite::memory:";

	public SQLiteRepository() {
		connect();
		createTable();
	}

	private void createTable() {
//				executeUpdate("drop table if exists "+SQL_TABLE);
				executeUpdate("create table "+SQL_TABLE+"(" + KEY_NAME +" varchar(30)  PRIMARY KEY," + KEY_QUANTITY + " INT);");
	}

	private void executeUpdate(String command) {
		if (conn != null) {
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(command);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public StoreItem loadItem(String productName) {
		StoreItem result;
		result = selectItem(productName);
		return result;
	}


	public StoreItem selectItem(String productName) {
		StoreItem result = null;
		String actName;
		int actQuantity;
		if (conn != null) {
			try {
				Statement Stmt = conn.createStatement();
				ResultSet rs = Stmt.executeQuery(
						"select * from "+SQL_TABLE+" where "+KEY_NAME + "='"+productName+"'");
				if (rs.next()) {
					actName = rs.getString(KEY_NAME);
					actQuantity = rs.getInt(KEY_QUANTITY);
					result =new StoreItem(actName,actQuantity);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	@Override
	public void saveItem(StoreItem item) {
		upsertItem(item);

	}

	public void upsertItem(StoreItem item) {
		executeUpdate("INSERT INTO "+SQL_TABLE+"(" + KEY_NAME +"," + KEY_QUANTITY + ") " +
				"VALUES('"+ item.getName() +"',"+item.getQuantity()+") "+
				"ON CONFLICT(" + KEY_NAME +") "+
				"DO UPDATE SET " + KEY_QUANTITY + "="+item.getQuantity()+";");
	}

	private void printDatabaseMetaData() throws SQLException {
		DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
		System.out.println("Driver name: " + dm.getDriverName());
		System.out.println("Driver version: " + dm.getDriverVersion());
		System.out.println("Product name: " + dm.getDatabaseProductName());
		System.out.println("Product version: " + dm.getDatabaseProductVersion());
	}

	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(dbURL);
//			printDatabaseMetaData();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

// only persist database
//	private void close()
//	{
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}

}
