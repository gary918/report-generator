/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator;

import java.util.*;

/**
 *
 * @author root
 */
public class DynamicRecord {

    private Hashtable records;

    public DynamicRecord() {
        records = new Hashtable();
    }

    public void setFields(String[] fields) {
    }

    public void setField(Object key, Object value) {
        if (value==null) value="null";
        records.put(key, value);
    }

    public String getValue(Object key) {
        return (String) records.get(key);
    }

    public Collection getValues() {
        return records.values();
    }

    public String showHeaders(boolean isForCreateTable) {
        String content = "";

        Set<String> keySet = records.keySet();
        Iterator<String> it = keySet.iterator();

        while (it.hasNext()) {
            String key = it.next();
            content = content + key;
            if (isForCreateTable) {
                content = content + " VARCHAR(1000)";
                if(key.equals("PROJECT_ID")){
                    content = content +" PRIMARY KEY";
                }
            }
            if (it.hasNext()) {
                content = content + ",";
            }
        }
        return content;

    }

    public String showColumns() {
        String content = "'";

        Set<String> keySet = records.keySet();
        Iterator<String> it = keySet.iterator();

        while (it.hasNext()) {
            String key = it.next();
            content = content + records.get(key) + "'";
            if (it.hasNext()) {
                content = content + ",'";
            }
        }
        return content;
    }

    public ArrayList getColumns() {
        ArrayList columns = new ArrayList();

        Set<String> keySet = records.keySet();
        Iterator<String> it = keySet.iterator();

        while (it.hasNext()) {
            columns.add(it.next());
        }
        return columns;
    }

    //Purify data: e.g. George Lan --> Requestor_Org = PSO
    public void purifyData() {
        String owner = (String) records.get("Requestor_Owner");
        String org = "N/A";
        if (owner!=null)
            org = Utility.getRequestorOrg(owner);
        records.put("Requestor_Org", org);
    }
}
