/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator;

import java.util.*;
import java.util.regex.*;

/**
 *
 * @author root
 */
public class Utility {

    public static String PROJECT_URL_PREFIX ="http://twiki.us.oracle.com/bin/view/ISVeProjects/";
    
    public static ArrayList<String> DATA_KEYS;
    static{
        DATA_KEYS = new ArrayList<String>();
        DATA_KEYS.add("PROJECT_ID");
        DATA_KEYS.add("Project_Name");
        DATA_KEYS.add("Segment");
        DATA_KEYS.add("Project_Type");
        DATA_KEYS.add("ISVe_Goal");
        DATA_KEYS.add("Project_Phase");
        DATA_KEYS.add("Execution_End");
        DATA_KEYS.add("Keywords");
    }
    
    static Hashtable replacePairs = null;
    static Hashtable purifyValuePairs = null;
    static Hashtable<String, String> ownerOrg = null;

    static {
        replacePairs = new Hashtable();
        replacePairs.put(" ", "_");
        replacePairs.put("#", "Num");
        replacePairs.put("$", "USD");
        replacePairs.put("(", "");
        replacePairs.put(")", "");
        replacePairs.put("/", "_");
        replacePairs.put("-", "_");
        replacePairs.put("&amp", "");
        replacePairs.put(";", "");
        replacePairs.put(",", "");
        replacePairs.put(".", "");
        
        purifyValuePairs = new Hashtable();
        purifyValuePairs.put("'"," ");  // better solution?
        purifyValuePairs.put("\"","\\\"");
        //purifyValuePairs.put("'","''");
        //purifyValuePairs.put("]","]]");

        ownerOrg = new Hashtable();
        ownerOrg.put("George Lan", "PSO");
        ownerOrg.put("Stephen Shi", "PSO");
        ownerOrg.put("Zuowei Wang", "PSO");
        ownerOrg.put("Apple Lv", "PSO");
        ownerOrg.put("Xiaomu Fu", "PSO");
        ownerOrg.put("Tristan Xie", "PSO");
        ownerOrg.put("Helen Zhong", "PSO");
        ownerOrg.put("Yulong Zhou", "PSO");
        ownerOrg.put("Sofia Kuo", "PSO");

        ownerOrg.put("Guang Wu", "S/W");

        ownerOrg.put("Gary Lo", "NEP");
        ownerOrg.put("Pan Su", "NEP");
        ownerOrg.put("Keven Du", "NEP");
        ownerOrg.put("Liu Yang", "NEP");
    }

    public static String createFieldName(String str) {
        if (str == null) {
            return "null";
        }

        str = str.trim();
        Set<String> keySet = replacePairs.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            str = str.replace(key, (String) replacePairs.get(key));
        }
        
        return str;
    }
    
    // For purify values which to be inserted into the database
    public static String purifyValue(String str){
        if (str == null) {
            return null;
        }

        str = str.trim();
        Set<String> keySet = purifyValuePairs.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            str = str.replace(key, (String) purifyValuePairs.get(key));
        }
        
        return str;
    }


    // get the organziation info of the requestor
    public static String getRequestorOrg(String owner) {
        String org = null;
        org = ownerOrg.get(owner);
        if (org == null) {
            org = "Other";
        }
        return org;
    }
    
    // return int percentage 
    public static int percentage(int actual, int total){
        return 100*actual/total;
    }
    
    public static String formTableHead(){
        String tHead = "<thead><tr>";
        Iterator it = DATA_KEYS.iterator();
        
        while(it.hasNext()){
            tHead = tHead + "<td>" + it.next()+ "</td>";
        }
        
        return tHead+"</tr></thead>";
    }
}
