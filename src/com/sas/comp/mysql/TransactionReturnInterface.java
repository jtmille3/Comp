package com.sas.comp.mysql;

import com.sas.comp.models.BaseModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interface for doing work with a DB Connection
 * Created by Philippe on 8/18/16.
 */
@FunctionalInterface
public interface TransactionReturnInterface {
    Object doTransaction(PreparedStatement statement) throws SQLException;
}
