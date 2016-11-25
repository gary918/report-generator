/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator.mongodb;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

/**
 *
 * @author gary
 */
public class Delegate {

    protected ArrayList freeRecords;
    protected String content;

    public Delegate() {
        freeRecords = new ArrayList();
        content = null;
    }

    public String query() {
        DynamicDatabase dd = new DynamicDatabase();
        return dd.query();
    }
    
    public String query(String condition) {
        DynamicDatabase dd = new DynamicDatabase();
        
        return dd.query(condition);
    }
    
    public String query(Hashtable conditions) {
        System.out.println("delegate...");
        DynamicDatabase dd = new DynamicDatabase();
        
        return dd.query(conditions);
    }

}
