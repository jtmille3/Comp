package com.sas.comp.mysql;

import java.sql.*;

public class Database {

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		//return DriverManager.getConnection("jdbc:mysql://pip.na.sas.com:3306/soccer", "soccer", "i<3soccer");
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/soccer", "soccer", "i<3soccer");
	}

    public static int doDBTransaction(String preparedStatement, TransactionInterface transactionInterface) {
        int generatedKey = -1;
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);


            PreparedStatement statement = conn.prepareStatement(preparedStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            transactionInterface.doTransaction(statement);
            conn.commit();
            try {
                final ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return generatedKey;
    }
}
