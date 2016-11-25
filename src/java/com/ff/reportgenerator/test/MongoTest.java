/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator.test;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.util.*;

/**
 *
 * @author gary
 */

/*
 * to start MongoDB: mongod --dbpath /Users/gary/Documents/mongodb/data/db
 * to connect MongoDb: mongo
*/
public class MongoTest {

    public MongoTest() {
    }

    public void testDb() {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("mydb");
            
            BasicDBObject doc = new BasicDBObject("name", "MongoDB").
                              append("type", "database").
                              append("count", 1).
                              append("info", new BasicDBObject("x", 203).append("y", 102));

//coll.insert(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        System.out.println("start mongo...");
        MongoTest mt = new MongoTest();
        mt.testDb();
    }

}
