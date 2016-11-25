/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator.mongodb;

import com.ff.reportgenerator.Utility;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * @author gary
 */
public class DynamicDatabase {

    protected static MongoClient mongoClient = null;

    protected static String DB_NAME = "mydb";

    public static DB getDB(String dbName) {
        if (mongoClient == null) {
            init();
        }
        return mongoClient.getDB(dbName);
    }

    private static void init() {
        try {
            if (mongoClient == null) {
                mongoClient = new MongoClient("localhost", 27017);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Select all
    public String query() {
        String records = "<table>" + Utility.formTableHead() + "<tbody>";

        DB myDB = getDB(DB_NAME);
        DBCollection coll = myDB.getCollection("projects");

        DBCursor ret = coll.find();
        BasicDBObject sort = new BasicDBObject("PROJECT_ID", 1);
        ret.sort(sort);

        try {
            while (ret.hasNext()) {
                records = records + "<tr>";

                DBObject rec = (DBObject) ret.next();
                Iterator keys = Utility.DATA_KEYS.iterator();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String value = (String) rec.get(key);
                    if (key.equals("PROJECT_ID")) {
                        records = records + "<td><a href=\"" + Utility.PROJECT_URL_PREFIX + value + "\">" + value + "</td>";
                    } else {
                        records = records + "<td>" + value + "</td>";

                    }
                }

                records = records + "</tr>";
                //System.out.println(rec);
            }
        } finally {
            ret.close();
        }

        records = records + "</tbody></table>";
        return records;
    }

    // Conditional queries
    public String query(String condition) {
        List<DBObject> list = null;

        DB myDB = getDB(DB_NAME);
        DBCollection coll = myDB.getCollection("projects");

        DBCursor ret = coll.find();
        BasicDBObject sort = new BasicDBObject("PROJECT_ID", 1);
        ret.sort(sort);

        try {
            list = ret.toArray();
        } finally {
            ret.close();
        }

        System.out.println("quering...." + condition);
        return list.toString();
    }

    // Conditional queries
    public String query(Hashtable conditions) {
        List<DBObject> list = null;

        DB myDB = getDB(DB_NAME);
        DBCollection coll = myDB.getCollection("projects");

        BasicDBObject cond = new BasicDBObject();

        Set<String> keySet = conditions.keySet();
        Iterator<String> it = keySet.iterator();

        while (it.hasNext()) {
            String key = it.next();
            String value = (String) conditions.get(key);
            if (value == null || value.equals("All")) {
                continue;
            }

            Pattern pattern = null;
            if (key.equals("Project_Phase")) {
                if (value.equals("Ongoing")) {
                    BasicDBList dlist = new BasicDBList();
                    dlist.add(new BasicDBObject(key, new BasicDBObject("$ne", "Completed")));
                    dlist.add(new BasicDBObject(key, new BasicDBObject("$ne", "Eng-Complete")));
                    dlist.add(new BasicDBObject(key, new BasicDBObject("$ne", "Canceled")));
                    dlist.add(new BasicDBObject(key, new BasicDBObject("$ne", "Publication")));
                    cond.append("$and", dlist);
                } else if (value.equals("Complete")) {
                    ArrayList<String> slist = new ArrayList();
                    slist.add("Completed");
                    slist.add("Eng-Complete");
                    slist.add("Publication");
                    cond.append(key, new BasicDBObject("$in", slist));

                    //Limited to FY15 projects
                    //pattern = Pattern.compile("^.*15-\\[.*$", Pattern.CASE_INSENSITIVE);
                    
                    //Limited to FY16 projects
                    pattern = Pattern.compile("^.*16-.*$", Pattern.CASE_INSENSITIVE);                    
                    cond.append("ISVe_Goal", pattern);
                }
            } else if (key.equals("Keywords") && value.equals("Non-OPI")) {  // not like 'OPI'
                pattern = Pattern.compile("^((?!OPI).)*$", Pattern.CASE_INSENSITIVE);
                cond.append(key, pattern);
            } else { // like '*OPI*'
                pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE);
                cond.append(key, pattern);
            }

            System.out.println(key + ":" + value);
        }

        DBCursor ret = coll.find(cond);

        BasicDBObject sort = new BasicDBObject("PROJECT_ID", 1);
        ret.sort(sort);

        try {
            list = ret.toArray();
        } finally {
            ret.close();
        }

        //System.out.println(list.toString());
        return list.toString();
    }

}
