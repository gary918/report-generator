/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;


/**
 *
 * @author root
 */
public class Delegate {

    private ArrayList freeRecords;

    public Delegate() {
        freeRecords = new ArrayList();
    }
    
    
    public void parsePage(String content, String tableName, int rowsNumber) {
        //PageParser parser = new PageParser(content, tableName);

        try {
            PageParser pp = new PageParser(content, tableName, rowsNumber);
            //freeRecords = pp.getTableContent();
            freeRecords = pp.getProjectDetails();
            //System.out.println(nl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    // invoked by generateReport()
    private void updateDatabase() {
        try {
            DynamicDatabase dd = new DynamicDatabase();
            dd.dropTables(freeRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Get the current number of records
    public int countTable() {
        int currentNumber = 0;
        try {
            DynamicDatabase dd = new DynamicDatabase();
            currentNumber = dd.countTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentNumber;
    }

    
    //parse the page & update the database
    public void generateReport() {
        String content = "http://twiki.us.oracle.com/bin/view/ISVeProjects/IsveKylinProjectsFY14";
        String tableName = "table2"; //html table
        int rows = 120;
        parsePage(content, tableName, 120);
        updateDatabase();
    }
    
    //get total number of freeRecords
    public int countFreeRecords(){
        return freeRecords.size();
    }

    // Create chart according to the user input
    public String createChart(String fieldName, String chartType, HttpSession session, JspWriter out) {
        String chartImageFileName = "";

        //chartImageFileName = Reporter.createPieChart(fieldName, chartType, session, out);
        chartImageFileName = Reporter.createChart(fieldName, chartType, 10000, session, out);
        return chartImageFileName;
    }

    public String createDefinedBarChart(String fieldName, HttpSession session, JspWriter out) {
        String[] chartItems = {"Solaris", "CMT", "M-Series", "X64", "OpenSolaris", "LDom", "Container", "OpenStorage", "ZFS", "MySQL", "Glassfish"};
        String chartImageFileName = "";

        chartImageFileName = Reporter.createDefinedBarChart(fieldName, "bar", chartItems, session, out);
        return chartImageFileName;
    }

    // select all data from the table
    public ArrayList queryDatabase() {
        ArrayList al = new ArrayList();
        try {
            DynamicDatabase dd = new DynamicDatabase();
            al = dd.queryDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    // select some data from the table
    public ArrayList queryDatabase(String condition) {
        ArrayList al = new ArrayList();
        try {
            DynamicDatabase dd = new DynamicDatabase();
            al = dd.queryDatabase(condition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    // e.g. 14-[1], completed
    // if isveGoal = "Performance" then (ISVE_GOAL like '%14-[1]% or ISVE_GOAL like '%14-[3]%') and (PROJECT_TYPE='Sizing Study' or PROJECT_TYPE='Performance Tuning' or PROJECT_TYPE='Benchmark') 
    public ArrayList queryProjects(String isveGoal, String projectPhase) {
        String sql = "select PROJECT_ID,PROJECT_NAME,ISV_COMMUNITY_NAME,PROJECT_TYPE,PROJECT_PHASE,SEGMENT,ISVE_GOAL,EXECUTION_END from TABLE1 where ";

        if (isveGoal.equals("Performance")) {
            sql = sql + "(ISVE_GOAL like '%14-[1]%' or ISVE_GOAL like '%14-[3]%') and (PROJECT_TYPE='Sizing Study' or PROJECT_TYPE='Performance Tuning' or PROJECT_TYPE='Benchmark') ";
        } else {
            sql = sql + "ISVE_GOAL like '%" + isveGoal + "%' ";
        }

        if (projectPhase.equals("Completed")) {
            sql = sql + "and (( PROJECT_PHASE='Eng-Complete' or PROJECT_PHASE='Completed') and (EXECUTION_END like '%Jun 2013' or EXECUTION_END like '%Jul 2013' or EXECUTION_END like '%Aug 2013' or EXECUTION_END like '%Sep 2013' or EXECUTION_END like '%Oct 2013' or EXECUTION_END like '%Nov 2013' or EXECUTION_END like '%Dec 2013' or EXECUTION_END like '%Jan 2014' or EXECUTION_END like '%Feb 2014' or EXECUTION_END like '%Mar 2014' or EXECUTION_END like '%Apr 2014' or EXECUTION_END like '%May 2014' ))";
        } else if (projectPhase.equals("Onging")) {
            sql = sql + "and ( PROJECT_PHASE<>'Eng-Complete' and PROJECT_PHASE<>'Completed' and PROJECT_PHASE<>'Canceled')";
        }

        ArrayList al = new ArrayList();
        try {
            DynamicDatabase dd = new DynamicDatabase();
            al = dd.queryDatabase(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    // e.g. 14-[1], completed, SEGMENT(column name)
    public ArrayList queryGroups(String isveGoal, String projectPhase, String groupName) {
        String sql = "select " + groupName + ",count(*) from TABLE1 where ";

        if (isveGoal.equals("Performance")) {
            sql = sql + "(ISVE_GOAL like '%14-[1]%' or ISVE_GOAL like '%14-[3]%') and (PROJECT_TYPE='Sizing Study' or PROJECT_TYPE='Performance Tuning' or PROJECT_TYPE='Benchmark') ";
        } else {
            sql = sql + "ISVE_GOAL like '%" + isveGoal + "%' ";
        }

        if (projectPhase.equals("Completed")) {
            sql = sql + "and (( PROJECT_PHASE='Eng-Complete' or PROJECT_PHASE='Completed') and (EXECUTION_END like '%Jun 2013' or EXECUTION_END like '%Jul 2013' or EXECUTION_END like '%Aug 2013' or EXECUTION_END like '%Sep 2013' or EXECUTION_END like '%Oct 2013' or EXECUTION_END like '%Nov 2013' or EXECUTION_END like '%Dec 2013' or EXECUTION_END like '%Jan 2014' or EXECUTION_END like '%Feb 2014' or EXECUTION_END like '%Mar 2014' or EXECUTION_END like '%Apr 2014' or EXECUTION_END like '%May 2014' ))";
        } else {
            sql = sql + "and ( PROJECT_PHASE<>'Eng-Complete' and PROJECT_PHASE<>'Completed' and PROJECT_PHASE<>'Canceled')";
        }

        sql = sql + " group by " + groupName;
        ArrayList al = new ArrayList();
        try {
            DynamicDatabase dd = new DynamicDatabase();
            al = dd.queryDatabase(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    public static void main(String[] args) {
        String content = "http://twiki.us.oracle.com/bin/view/ISVeProjects/IsveKylinProjectsFY14";
        String tableName = "table1";
        Delegate d = new Delegate();

        //d.queryDatabase();
    }
}
