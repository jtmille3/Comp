package com.sas.comp.mysql;

import com.sas.comp.models.BaseModel;

import java.sql.*;

public class Database {

	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://pip.na.sas.com:3306/soccer", "soccer", "i<3soccer");
        //return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/soccer", "soccer", "i<3soccer");
	}

    public static void doVoidTransaction(String preparedStatement, TransactionVoidInterface transactionVoidInterface) {
        doDBTransaction(preparedStatement, transactionVoidInterface);
    }

    public static <T> T doReturnTransaction(Class<T> type, String preparedStatement, TransactionReturnInterface transactionInterface)
    {
        return type.cast(doDBTransaction(preparedStatement, transactionInterface));
    }

    private static Object doDBTransaction(String preparedStatement, Object transactionInterface) {
        Connection conn = null;
        Object returnObject = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);


            PreparedStatement statement = conn.prepareStatement(preparedStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            if(transactionInterface instanceof TransactionReturnInterface) {
                returnObject = ((TransactionReturnInterface)transactionInterface).doTransaction(statement);
            } else {
                ((TransactionVoidInterface)transactionInterface).doTransaction(statement);
            }
            conn.commit();
            if(returnObject != null && returnObject instanceof BaseModel) {
                try {
                    final ResultSet rs = statement.getGeneratedKeys();
                    if (rs.next()) {
                        ((BaseModel)returnObject).setId(rs.getInt(1));
                    }
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
        return returnObject;
    }
}
