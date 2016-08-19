package com.sas.comp.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interface for doing work with a DB Connection
 * Created by Philippe on 8/18/16.
 */
public interface TransactionInterface {
    void doTransaction(PreparedStatement statement) throws SQLException;
}
