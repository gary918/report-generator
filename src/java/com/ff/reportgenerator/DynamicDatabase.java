/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

import org.jfree.data.general.*;
import org.jfree.data.category.*;

/**
 *
 * @author root
 */
public class DynamicDatabase {

    String tableName = "";
    String databaseURL = "jdbc/sample";
    // String databaseURL = databaseURL;
    DataSource dataSource = null;

    public DynamicDatabase() throws Exception {
        tableName = "table1";
        Context ctxt = new InitialContext();
        dataSource = (DataSource) ctxt.lookup(databaseURL);
    }

    public String getTableName() {
        return tableName;
    }

    public Connection getConnection() throws Exception {
        Connection con = null;
        con = dataSource.getConnection();
        return con;
    }

    private String createTableStatement(DynamicRecord record) {
        String stmt = "create table " + tableName + "(" + record.showHeaders(true) + ")";
        System.out.println(stmt);
        return stmt;
    }

    private String insertTableStatement(DynamicRecord record) {
        String stmt = "insert into " + tableName + "(" + record.showHeaders(false) + ") values(" + record.showColumns() + ")";
        System.out.println(stmt);
        return stmt;
    }

    //Count the current record numbers in the table
    public int countTable() throws Exception {
        int currentNumber = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        con = dataSource.getConnection();
        stmt = con.createStatement();
        String sql = "select count(*) from " + tableName;
        rs = stmt.executeQuery(sql);

        if (rs.next()) {
            currentNumber = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        con.close();
        return currentNumber;
    }

    //Drop old table & create new one, insert data, obsolate method
    public void dropTables(ArrayList records) throws Exception {
        String sql = "";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        if (dataSource == null) {
            throw new Exception("null datasource!");
        }

        if (records.isEmpty()) {
            return;
        }

        con = dataSource.getConnection();
        stmt = con.createStatement();

        // Drop if exists tableName
        DatabaseMetaData dbmd = con.getMetaData();
        rs = dbmd.getTables(null, "APP", tableName.toUpperCase(), null);
        if (rs.next()) {
            stmt.execute("drop table " + tableName);
        }
        rs = null;

        // create table
        stmt.execute(createTableStatement((DynamicRecord) records.get(0)));
        //System.out.println(createTableStatement((DynamicRecord)records.get(0)));

        //stmt.execute("insert into "+tableName+"(id,data) values(1973,'GaryWang')");
        Iterator it = records.iterator();
        while (it.hasNext()) {
            DynamicRecord record = (DynamicRecord) it.next();
            //System.out.println(insertTableStatement(record));
            stmt.execute(insertTableStatement(record));

        }

        sql = "select * from " + tableName;
        rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println(rs.getString(2));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public void updateDatabaseWS(ArrayList records, DelegateWS dg) throws Exception {
        String sql = "";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        int total = records.size();

        if (dataSource == null) {
            throw new Exception("null datasource!");
        }

        if (records.isEmpty()) {
            return;
        }

        con = dataSource.getConnection();
        stmt = con.createStatement();

        // Drop if exists tableName
        DatabaseMetaData dbmd = con.getMetaData();
        rs = dbmd.getTables(null, "APP", tableName.toUpperCase(), null);
        if (rs.next()) {
            stmt.execute("drop table " + tableName);
        }
        rs = null;

        // create table
        stmt.execute(createTableStatement((DynamicRecord) records.get(0)));
        Iterator it = records.iterator();
        int count = 0;
        while (it.hasNext()) {
            DynamicRecord record = (DynamicRecord) it.next();
            //System.out.println(insertTableStatement(record));
            if (record != null) {
                stmt.execute(insertTableStatement(record));
                count++;
            } else {
                System.out.println("NULL RECORD!");
            }
            //count++;
            dg.sendUpdate(Utility.percentage(count, total)+"");
        }

        sql = "select * from " + tableName;
        rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println(rs.getString(2));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public void updateDatabase() throws Exception {
        String sql = "";
        String url = "";

        DataSource ds = null;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        Context ctxt = new InitialContext();
        ds = (DataSource) ctxt.lookup(databaseURL);

        if (ds != null) {
            con = ds.getConnection();

            stmt = con.createStatement();
            sql = "select * from A";
            rs = stmt.executeQuery(sql);

        }

        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        rs.close();
        stmt.close();
        con.close();

    }

    //useful SQL: select segment,count(segment) as segment from table1 group by segment
    public ArrayList queryDatabase() throws Exception {
        String sql = "";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList al = new ArrayList();

        con = dataSource.getConnection();
        stmt = con.createStatement();

        sql = "select * from " + tableName;
        rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();

        int ccount = rsmd.getColumnCount();

        while (rs.next()) {
            DynamicRecord dr = new DynamicRecord();
            for (int i = 1; i <= ccount; i++) {
                String columnName = rsmd.getColumnName(i);
                String value = rs.getString(i);
                System.out.println(columnName + ":" + value);
                dr.setField(columnName, value);
            }
            al.add(dr);

        }

        rs.close();
        stmt.close();
        con.close();

        return al;
    }

    public DefaultPieDataset queryDatabase(String fieldName, int scope) throws Exception {
        //String sql = "select " + fieldName + ",count(" + fieldName + ") as " + fieldName + " from " + tableName + " group by " + fieldName;
        String sql = "select " + fieldName + ",count(" + fieldName + ") as " + fieldName + " from " + tableName + " where status='Completed' group by " + fieldName + " order by count(" + fieldName + ") desc";

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        DefaultPieDataset dataset = new DefaultPieDataset();
        con = dataSource.getConnection();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        int count = 1;
        int othersCount = 0;

        while (rs.next()) {
            if (count <= scope) {
                dataset.setValue(rs.getString(1), rs.getInt(2));
                count++;
            } else {
                othersCount = othersCount + rs.getInt(2);
            }
        }

        if (othersCount > 0) {
            dataset.setValue("Other", othersCount);
        }

        rs.close();
        stmt.close();
        con.close();

        return dataset;
    }

    public DefaultCategoryDataset queryDatabase(String fieldName, String[] chartItems) throws Exception {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        con = dataSource.getConnection();
        stmt = con.createStatement();

        for (int i = 0; i < chartItems.length; i++) {
            String sql = "select count(*) from " + tableName + " where status='Completed' and " + fieldName + " like '%" + chartItems[i] + "%'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                dataset.addValue(rs.getInt(1), "null", chartItems[i]);
            }
        }

        rs.close();
        stmt.close();
        con.close();

        return dataset;
    }

    public ArrayList queryDatabase(String sql) throws Exception {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList al = new ArrayList();

        con = dataSource.getConnection();
        stmt = con.createStatement();

        //sql = "select PROJECT_ID,PROJECT_NAME,PROJECT_PHASE,SEGMENT,ISVE_GOAL,EXECUTION_END from " + tableName + " where " + condition;
        System.out.println(sql);
        rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();

        int ccount = rsmd.getColumnCount();

        while (rs.next()) {
            DynamicRecord dr = new DynamicRecord();
            for (int i = 1; i <= ccount; i++) {
                String columnName = rsmd.getColumnName(i);
                String value = rs.getString(i);
                //System.out.println(columnName + ":" + value);
                dr.setField(columnName, value);
            }
            al.add(dr);

        }

        rs.close();
        stmt.close();
        con.close();

        return al;
    }
}
