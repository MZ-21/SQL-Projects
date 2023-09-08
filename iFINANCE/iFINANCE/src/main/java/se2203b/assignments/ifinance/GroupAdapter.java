package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GroupAdapter {
    Connection connection;

    public GroupAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        //stmt.execute("DROP TABLE Groups");
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Groups");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }


//        try {
//
//            // Create the table
//            stmt.execute("CREATE TABLE AccountCategory ("
//                    + "names VARCHAR(60) NOT NULL PRIMARY KEY,"
//                    + "types VARCHAR(50) NOT NULL"
//                    + ")");
//        } catch (SQLException ex) {
//            // No need to report an error.
//            // The table exists and may have some data.
//            System.out.println(ex.getMessage());
//        }

            try {

                // Create the table
                stmt.execute("CREATE TABLE Groups("
                        + "ID INTEGER PRIMARY KEY,"
                        + "NAMES VARCHAR(50) NOT NULL,"
                        + "PARENT INT,"
                        + "ELEMENT VARCHAR(60) NOT NULL REFERENCES AccountCategory(names)"
                        //+ "FOREIGN KEY(PARENT) REFERENCES Groups(ID)"
                        + ")");
                addGroup();
            } catch (SQLException ex) {
                // No need to report an error.
                // The table exists and may have some data.
                System.out.println(ex.getMessage());

            }
            updateGroupNull();

    }

    private void addGroup() throws SQLException {
        AccountCategory account = new AccountCategory("Assets", "Debit");
//        AccountCategory account2 = new AccountCategory("Liabilities", "Credit");
//        AccountCategory account3 = new AccountCategory("Income", "Credit");
//        AccountCategory account4 = new AccountCategory("Expenses", "Debit");


        Group group1 = new Group(getMax(), "Fixed Assets", new Group().getParent(),account);
        Group group2 = new Group(getMax(), "Investments", new Group().getParent(),account);
//
//        Group group2 = new Group(2, "Investments", getGroup(), accD.findRecord());
//        Group group3 = new Group(3, "Branch/divisions", getGroup(), accD.findRecord());
//        Group group4 = new Group(4, "Cash in hand", getGroup(), accD.findRecord());
//        Group group5 = new Group(5, "Bank accounts", getGroup(), accD.findRecord());
//        Group group6 = new Group(6, "Deposits (assets)", getGroup(), accD.findRecord());
//        Group group7 = new Group(7, "Advances (assets)", getGroup(), accD.findRecord());
//        Group group8 = new Group(8, "Capital account", getGroup(), accD.findRecord());
//        Group group9 = new Group(9, "Long term loans", getGroup(), accD.findRecord());
//        Group group10 = new Group(10, "Current liabilities", getGroup(), accD.findRecord());
//        Group group11 = new Group(11, "Reserves and surplus", getGroup(), accD.findRecord());
//        Group group12 = new Group(12, "Sales account", getGroup(), accD.findRecord());
//        Group group13 = new Group(13, "Purchase account", getGroup(), accD.findRecord());
//        Group group14 = new Group(14, "Expenses (direct)", getGroup(), accD.findRecord());
//        Group group15 = new Group(15, "Expenses (direct)", getGroup(), accD.findRecord());
//        Group group16 = new Group(16, "Expenses (direct)", getGroup(), accD.findRecord());
//        Group group17 = new Group(17, "Expenses (direct)", getGroup(), accD.findRecord());
//        Group group18 = new Group(18, "Expenses (direct)", getGroup(), accD.findRecord());
//        Group group19 = new Group(19, "Expenses (direct)", getGroup(), accD.findRecord());
//        Group group20 = new Group(20, "Expenses (direct)", getGroup(), accD.findRecord());
//        Group group21 = new Group(21, "Expenses (direct)", getGroup(), accD.findRecord());


        insertRecord(group1);
        insertRecord(group2);


//        updateGroup(group1);
//        insertRecord(group2);
//        insertRecord(group3);
//        insertRecord(group4);
//        insertRecord(group5);
//        insertRecord(group6);
//        insertRecord(group7);
//        insertRecord(group8);
//        insertRecord(group9);
//        insertRecord(group10);
//        insertRecord(group11);
//        insertRecord(group12);
//        insertRecord(group13);
//        insertRecord(group14);
//        insertRecord(group15);
//        insertRecord(group16);
//        insertRecord(group17);
//        insertRecord(group18);
//        insertRecord(group19);
//        insertRecord(group20);
//        insertRecord(group21);

    }

    public void insertRecord(Group data) throws SQLException {
        Statement stmt = connection.createStatement();

//        stmt.executeUpdate("INSERT INTO Groups (ID, NAMES,PARENT,ELEMENT) "
//                + "VALUES ("
//                + (getMax()+1) + ", '"  // We just assign a new id before we save the record
//                + data.getName() + "', "
//                + data.getParent() + ",'"
//                + data.getElement().getName()
//                + "')"
//        );

//        stmt.executeUpdate("INSERT INTO Groups (ID,NAMES,PARENT,ELEMENT) VALUES ("+(getMax()+1)+",'"+data.getName()+"',"+data.getParent()+",'"+data.getElement()+"')");
        //updateGroup(data.getParent());
        try(PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Groups (ID, NAMES,PARENT,ELEMENT) VALUES (?,?,"+data.getParent()+",?)")) {
            insertStatement.setInt(1, getMax()+1);
            insertStatement.setString(2, data.getName());
            // Do NOT do this in a real application, hash is with a password hash algorithm

            insertStatement.setString(3, data.getElement().getName());

            insertStatement.executeUpdate();
        }
        catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
            System.out.println(ex.getMessage());

        }
    }
    public int getMax() throws SQLException {
        Integer num = 0;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM Groups");
        if (rs.next()) num = rs.getInt(1);
        return num;
    }
    public ObservableList<String> getDataBaseInfo() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        // Create a Statement object
        Statement stmt = connection.createStatement();

        ResultSet rs;


        String statementQ = "SELECT * FROM Groups ";

        rs = stmt.executeQuery(statementQ);
        while (rs.next()) {
            list.add(String.format("%s-%s-%s-%s",rs.getInt("ID"),rs.getString("NAMES"),rs.getInt("PARENT"),rs.getString("ELEMENT")));

        }
        return list;
    }
    public ObservableList<Integer> getCHILDRENGroups(Integer id, ObservableList<Integer> listG) throws SQLException {

        // Create a Statement object
        Statement stmt = connection.createStatement();

        ResultSet rs;


        String statementQ = "SELECT * FROM Groups " +
                "WHERE ID = " + id +"";


//        PreparedStatement statement = connection.prepareStatement(statementQ);
        rs = stmt.executeQuery(statementQ);
        //Integer ids = 0;

        if (rs.next()) {}

        if (rs.getInt("PARENT") == 0) {
           listG.add(rs.getInt("ID"));

        }
        else{
            listG.add(rs.getInt("ID"));
            getCHILDRENGroups(rs.getInt("PARENT"),listG);

        }
//        statement.executeUpdate();
        return listG;
    }
    //Modify one record based on the given object
    public void updateGroupNull() throws SQLException {
        // ObservableList<String> list = FXCollections.observableArrayList();
        // Create a Statement object
        //Statement stmt = connection.createStatement();

        ResultSet rs;


        String statementQ = "UPDATE Groups " +
                "SET PARENT = ?" +
                "WHERE " +
                "PARENT IS NULL";

        //String sql = "SELECT Password FROM Account where Password= '" + oldPass +"'";
        //rs = stmt.executeQuery(sql);
        PreparedStatement statement = connection.prepareStatement(statementQ);
        statement.setInt(1, 0);

        statement.executeUpdate();

    }

    public void updateGroupNotNull(int parentID, String nameG) throws SQLException {
        // ObservableList<String> list = FXCollections.observableArrayList();
        // Create a Statement object
        //Statement stmt = connection.createStatement();

        ResultSet rs;


        String statementQ = "UPDATE Groups " +
                "SET PARENT = ?" +
                "WHERE " +
                "NAMES = ?";

        //String sql = "SELECT Password FROM Account where Password= '" + oldPass +"'";
        //rs = stmt.executeQuery(sql);
        PreparedStatement statement = connection.prepareStatement(statementQ);


        statement.setInt(1, parentID);
        statement.setString(2,nameG);

        statement.executeUpdate();

    }

    public Group recursionGroup(Integer id) throws SQLException {
        ResultSet rs;
        Statement stmt = connection.createStatement();
        System.out.println(id);
        String statementQ = "SELECT * FROM Groups " +
                "WHERE ID = " + id +"";


        rs = stmt.executeQuery(statementQ);

        if(rs.next()){};

        if (rs.getObject("PARENT") == null) {
            return new Group();
            }
        else if(rs.next()) {
                recursionGroup(rs.getInt("PARENT"));
            }
        return new Group();
    }
    public String findCategory(String name) throws SQLException {
        Statement stmt=connection.createStatement();
        ResultSet rs;
        ObservableList<AccountCategory> cat= FXCollections.observableArrayList();
        AccountCategory category= new AccountCategory();
        String sql= "SELECT Element FROM Groups "+
                "WHERE "+
                "Names ='" +name+ "'";
        rs= stmt.executeQuery(sql);
        while(rs.next()){
            category.setType(rs.getString("Element"));
        }

        return  category.getType();
    }


    public String getGroupsName(Integer id) throws SQLException {
//        ObservableList<Group> gp = FXCollections.observableArrayList();
        String gp = "";
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT NAMES FROM Groups " +
                "WHERE ID = " + id +"";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            // note that, this loop will run only once
            gp = rs.getString("NAMES");
        }
        return gp;
    }

    public ObservableList<String> getName() throws SQLException {
//        ObservableList<Group> gp = FXCollections.observableArrayList();
        ObservableList<String> n = FXCollections.observableArrayList();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT NAMES FROM Groups";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            // note that, this loop will run only once
            n.add(rs.getString("NAMES"));
        }
        return n;
    }
    public ObservableList<String> getParentInfo(String name) throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        // Create a Statement object
        Statement stmt = connection.createStatement();

        ResultSet rs;


        String statementQ = "SELECT * FROM Groups WHERE NAMES = '" + name +"'";

        //String sql = "SELECT Password FROM Account where Password= '" + oldPass +"'";
        //rs = stmt.executeQuery(sql);
        rs = stmt.executeQuery(statementQ);
        while (rs.next()) {
            list.add(String.format("%s-%s-%s",rs.getInt("ID"),rs.getInt("PARENT"),rs.getString("ELEMENT")));

        }
        return list;
    }
    public void deleteInfo(String name) throws SQLException{
        ObservableList<String> list = FXCollections.observableArrayList();
        // Create a Statement object
        Statement stmt = connection.createStatement();

        ResultSet rs;


        String statementQ = "DELETE FROM Groups " +
                "WHERE NAMES = '" + name +"'";
        //"ID = ?";

        //String sql = "SELECT Password FROM Account where Password= '" + oldPass +"'";
        // rs = stmt.executeQuery(statementQ);
        // if(rs.next());
        PreparedStatement statement = connection.prepareStatement(statementQ);

        //statement.setInt(1,id);
        statement.executeUpdate();
    }
    public void updateGInfo(String newN ,String oldN) throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        // Create a Statement object
        Statement stmt = connection.createStatement();

        ResultSet rs;


        String statementQ = "UPDATE Groups " +
                "SET NAMES = ? " +
                "WHERE " +
                "NAMES = ?";

        //String sql = "SELECT Password FROM Account where Password= '" + oldPass +"'";
        //rs = stmt.executeQuery(sql);
        PreparedStatement statement = connection.prepareStatement(statementQ);


        statement.setString(1,newN);
        statement.setString(2,oldN);
        statement.executeUpdate();
    }
}
