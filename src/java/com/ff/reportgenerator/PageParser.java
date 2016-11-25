/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator;

import java.util.*;

import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.Parser;
import org.htmlparser.util.*;
import org.htmlparser.filters.*;

/**
 *
 * @author root
 */
public class PageParser {

    String pageURL = null;
    String tableName = null;
    int rowsNumber = 0;

    public static void main(String[] args) {
        String content = "http://twiki.us.oracle.com/bin/view/ISVeProjects/IsveKylinProjectsFY14";
        String tableName = "table2";

        try {
            PageParser ph = new PageParser(content, tableName);
            ph.getProjectDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //which table, how many rows
    public PageParser(String pageURL, String tableName, int rowsNumber) {
        this.pageURL = pageURL;
        this.tableName = tableName;
        this.rowsNumber = rowsNumber;
    }

    public PageParser(String pageURL, String tableName) {
        this.pageURL = pageURL;
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void getTableRows(NodeList nodelist) throws Exception {
        //TagNameFilter rowFilter = new TagNameFilter("thread");
        NodeClassFilter rowFilter = new NodeClassFilter(TableRow.class);
        NodeClassFilter colFilter = new NodeClassFilter(TableColumn.class);
        NodeList rowList = null;
        rowList = nodelist.extractAllNodesThatMatch(rowFilter, true);
        //System.out.println(rowList);
        SimpleNodeIterator it = rowList.elements();
        while (it.hasMoreNodes()) {
            Node node = it.nextNode();
            NodeList children = node.getChildren();
            NodeList colList = children.extractAllNodesThatMatch(colFilter);
            SimpleNodeIterator colIt = colList.elements();
            while (colIt.hasMoreNodes()) {
                TableColumn col = (TableColumn) colIt.nextNode();
                //col.getText();
                System.out.println(col.toHtml());
            }
        }
    }


    // Get all project IDs on one page, by filtering the duplicated projects, Nov 18, 2013
    public ArrayList getProjectIDs() {
        ArrayList projectIDs = new ArrayList();

        String encoding = "ISO-8859-1";
        String containStr = "ISVeProject201";

        try {
            Parser parser = new Parser();
            parser.setURL(pageURL);
            if (encoding == null) {
                parser.setEncoding(parser.getEncoding());
            } else {
                parser.setEncoding(encoding);
            }

            NodeFilter filter = new StringFilter(containStr);
            NodeList list = parser.extractAllNodesThatMatch(filter);
            for (int i = 0; i < list.size(); i++) {
                Node node = (Node) list.elementAt(i);
                String projectID = node.toHtml();
                //System.out.println(node.toHtml());
                if (!projectIDs.contains(projectID) && projectID.startsWith(containStr) && projectID.length()== 25) {
                    projectIDs.add(projectID);
                }
            }

            System.out.println("total projects:" + projectIDs.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectIDs;
    }
    
    // return JSON string of a project
    private String getProjectDetailsMg(String projectID){
        String project = "{'PROJECT_ID':'" + projectID +"',";
        
        String projectURL = Utility.PROJECT_URL_PREFIX + projectID;

        NodeList nodelist = null;
        NodeFilter[] nodeFilter = new NodeFilter[2];
        try {
            Parser parser = new Parser(projectURL);//contructor
            //parser.setEncoding("GB2312");//set encode
            //TagNameFilter tnf = new TagNameFilter();

            //TagNameFilter tableFilter = new TagNameFilter("table");//get the table content
            NodeClassFilter tableFilter = new NodeClassFilter(TableTag.class);
            HasAttributeFilter tableAttribute = new HasAttributeFilter("class", "twikiFormTable");//have the attribute "class"
            //HasAttributeFilter tableAttribute2 = new HasAttributeFilter("width", "100%");//hava the attribute "width"
            nodeFilter[0] = tableFilter;
            nodeFilter[1] = tableAttribute;
            //nodeFilter[2] = tableAttribute2;
            AndFilter andFilter = new AndFilter(nodeFilter);//to link the three filter that above together
            nodelist = parser.extractAllNodesThatMatch(andFilter);//get the result that fit for the filter
            //nodelist.remove(nodelist.size() - 1);//to remove the last element
            //getTableRows(nodelist);
            if (nodelist.size() == 0) {
                return null;
            }
            TableTag table = (TableTag) nodelist.elementAt(0);

            //get rows
            TableRow[] rows = table.getRows();

            Utility util = new Utility();
            int rowLength = rows.length;
            for (int i = 1; i < rowLength; i++) {
                TableColumn[] columns = rows[i].getColumns();
                project = project + 
                        "'" + Utility.createFieldName(columns[0].toPlainTextString())+"':'"
                        + Utility.purifyValue(columns[1].toPlainTextString()) + "',";
                //System.out.println(columns[0].toPlainTextString().trim() + columns[1].toPlainTextString().trim());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        project = project + "}";
        return project;
    }

    // Get project details for a singal page
    private DynamicRecord getProjectDetails(String projectID) {
        DynamicRecord dr = new DynamicRecord();
        String projectURL = Utility.PROJECT_URL_PREFIX + projectID;

        NodeList nodelist = null;
        NodeFilter[] nodeFilter = new NodeFilter[2];
        try {
            Parser parser = new Parser(projectURL);//contructor
            //parser.setEncoding("GB2312");//set encode
            //TagNameFilter tnf = new TagNameFilter();

            //TagNameFilter tableFilter = new TagNameFilter("table");//get the table content
            NodeClassFilter tableFilter = new NodeClassFilter(TableTag.class);
            HasAttributeFilter tableAttribute = new HasAttributeFilter("class", "twikiFormTable");//have the attribute "class"
            //HasAttributeFilter tableAttribute2 = new HasAttributeFilter("width", "100%");//hava the attribute "width"
            nodeFilter[0] = tableFilter;
            nodeFilter[1] = tableAttribute;
            //nodeFilter[2] = tableAttribute2;
            AndFilter andFilter = new AndFilter(nodeFilter);//to link the three filter that above together
            nodelist = parser.extractAllNodesThatMatch(andFilter);//get the result that fit for the filter
            //nodelist.remove(nodelist.size() - 1);//to remove the last element
            //getTableRows(nodelist);
            if (nodelist.size() == 0) {
                return null;
            }
            TableTag table = (TableTag) nodelist.elementAt(0);

            //get rows
            TableRow[] rows = table.getRows();
            
            // insert the URL
            dr.setField("PROJECT_ID",projectID);

            Utility util = new Utility();
            int rowLength = rows.length;
            for (int i = 1; i < rowLength; i++) {
                TableColumn[] columns = rows[i].getColumns();
                dr.setField(Utility.createFieldName(columns[0].toPlainTextString()), Utility.purifyValue(columns[1].toPlainTextString()));
                //System.out.println(columns[0].toPlainTextString().trim() + columns[1].toPlainTextString().trim());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dr;
    }

    public ArrayList getProjectDetails() {
        ArrayList records = new ArrayList();
        String encoding = "ISO-8859-1";

        // Get proejct IDs
        ArrayList projectIDs = getProjectIDs();

        // Get project details & input into records
        // Iterate the projects to get the details
        Iterator it = projectIDs.iterator();
        while (it.hasNext()) {
            String projectID = (String)it.next();
            System.out.println(projectID);
            DynamicRecord dr = getProjectDetails(projectID);
            records.add(dr);
        }

        return records;
    }
    
    // For WS call
    public ArrayList getProjectDetails(DelegateWS dg) throws Exception{
        ArrayList records = new ArrayList();
        //String encoding = "ISO-8859-1";

        // Get proejct IDs
        ArrayList projectIDs = getProjectIDs();

        // Get project details & input into records
        // Iterate the projects to get the details
        Iterator it = projectIDs.iterator();
        int total = projectIDs.size();
        dg.sendUpdate("STATUS:" + total + " projects found.");
        
        int count = 0;
        while (it.hasNext()) {
            String projectID = (String)it.next();
            System.out.println(projectID);
            DynamicRecord dr = getProjectDetails(projectID);
            records.add(dr);
            count++;
            dg.sendUpdate(Utility.percentage(count, total) + "");
        }

        return records;
    }
}
