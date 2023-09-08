package se2203b.assignments.ifinance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountCategoryAdapter {
    Connection connection;

    public AccountCategoryAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        //stmt.execute("DROP TABLE AccountCategory");
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE AccountCategory");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }


            try {

                // Create the table
                stmt.execute("CREATE TABLE AccountCategory("
                        + "names VARCHAR(60) NOT NULL PRIMARY KEY,"
                        + "types VARCHAR(60) NOT NULL"
                        + ")");
                addAccountCategory();
            } catch (SQLException ex) {
                // No need to report an error.
                // The table exists and may have some data.
                System.out.println(ex.getMessage());

            }

    }

    private void addAccountCategory() throws SQLException {

        AccountCategory account = new AccountCategory("Assets", "Debit");
        AccountCategory account2 = new AccountCategory("Liabilities", "Credit");
        AccountCategory account3 = new AccountCategory("Income", "Credit");
        AccountCategory account4 = new AccountCategory("Expenses", "Debit");

        // save use profile
        insertRecord(account);
        insertRecord(account2);
        insertRecord(account3);
        insertRecord(account4);
    }

    public void insertRecord(AccountCategory data) throws SQLException {
        Statement stmt = connection.createStatement();

        stmt.executeUpdate("INSERT INTO AccountCategory(names,types) "
                + "VALUES ('"
                + data.getName() + "', '"
                + data.getType() +
                "')"
        );

    }
////
////    public int getMax() throws SQLException {
////        int num = 0;
////        Statement stmt = connection.createStatement();
////        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM NonAdminUser");
////        if (rs.next()) num = rs.getInt(1);
////        return num;
////    }
//
//    // Modify one record based on the given object
////    public void updateRecord(NonAdminUser data) throws SQLException {
////        Statement stmt = connection.createStatement();
////        stmt.executeUpdate("UPDATE NonAdminUser "
////                + "SET id = " + data.getID() + ", "
////                + "fullName = '" + data.getFullName() + "', "
////                + "address = '" + data.getAddress() + "', "
////                + "email = '" + data.getEmail() + "', "
////                + "uAccount = '" + data.getuAccount().getUName() + "' "
////                + "WHERE id = " + data.getID());
////    }
//
//    //
////    // Delete one record based on the given object
////    public void deleteRecord(NonAdminUser data) throws SQLException {
////        Statement stmt = connection.createStatement();
////        // user profile
////        System.out.println("DELETE FROM NonAdminUser WHERE id = " + data.getID());
////        stmt.executeUpdate("DELETE FROM NonAdminUser WHERE id = " + data.getID());
////        // delete user account
////        stmt.executeUpdate("DELETE FROM UserAccount WHERE uName = '" + data.getuAccount().getUName() + "'");
////    }
////
////
//    // get one record, that matches the given name value

    public AccountCategory parentAccount(String name) throws SQLException {
        ResultSet rs;
        Statement stmt = connection.createStatement();
        String statementQ = "SELECT * FROM AccountCategory WHERE names = '"+name+"'";

        rs = stmt.executeQuery(statementQ);

        if(rs.next());
        AccountCategory  accountCategory1= new AccountCategory(rs.getString("names"),rs.getString("types"));
        return  accountCategory1;
    }
    }



