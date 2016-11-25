/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator.mongodb;

import com.ff.reportgenerator.Utility;
import static com.ff.reportgenerator.mongodb.DynamicDatabase.DB_NAME;
import static com.ff.reportgenerator.mongodb.DynamicDatabase.getDB;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author gary
 */
public class DynamicDatabaseWS {

    public void updateDatabase(ArrayList projects, DelegateWS dg) throws Exception {
        DB myDB = getDB(DB_NAME);
        myDB.dropDatabase(); // clear old records

        myDB = getDB(DB_NAME);
        DBCollection coll = myDB.getCollection("projects");

        int total = projects.size();
        //System.out.println(total+" records!");

        Iterator it = projects.iterator();
        int count = 0;
        while (it.hasNext()) {
            String json = (String) it.next();
            if (json != null) {
                DBObject dbObject = (DBObject) JSON.parse(json);
                coll.insert(dbObject);
                count++;
            } else {
                System.out.println("NULL RECORD!");
            }
            //count++;
            dg.sendUpdate(Utility.percentage(count, total) + "");
        }

    }

}
