package org.apache.ibatis.transaction.managed;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class ManagedTransaction implements Transaction {

  private static final Log log = LogFactory.getLog(ManagedTransaction.class);

  private Connection connection;

  public ManagedTransaction(Connection connection) {
    this.connection = connection;
  }

  public Connection getConnection() {
    return connection;
  }

  public void commit() throws SQLException {
    // Does nothing
  }

  public void rollback() throws SQLException {
    // Does nothing
  }

  public void close() throws SQLException {
    try {
      if (connection != null) connection.close();
    } catch (SQLException e) {
      // Log and ignore.  Nothing more that should be done here.
      log.error(e.getMessage(), e);
    }
  }

}
