package com.sas.comp.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interface for doing work with a DB Connection
 * Created by Philippe on 8/18/16.
 */
@FunctionalInterface
public interface TransactionVoidInterface {
    void doTransaction(PreparedStatement statement) throws SQLException;
}
