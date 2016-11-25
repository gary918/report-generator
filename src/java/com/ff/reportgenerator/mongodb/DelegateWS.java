/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator.mongodb;

import java.io.IOException;
import java.util.ArrayList;
import javax.websocket.Session;

/**
 *
 * @author gary
 */
public class DelegateWS extends Delegate {

    private Session mySession; // websocket session
    
    public DelegateWS(String twikiPage, Session frontSession){
        freeRecords = new ArrayList();
        mySession = frontSession;
        content = twikiPage;
    }
    // Websocket update message for the page
    public void sendUpdate(String message) throws IOException{
        if(mySession!=null){
            mySession.getBasicRemote().sendText(message);
        }
        else{
            System.out.println("ERROR: No session!");
        }
    }
    
    public void parsePage(String content, String tableName, int rowsNumber) {
        //PageParser parser = new PageParser(content, tableName);

        try {
            PageParser pp = new PageParser(content, tableName, rowsNumber);
            //freeRecords = pp.getTableContent();
            freeRecords = pp.getProjectDetails(this);
            //System.out.println(nl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDatabase() {
        try {
            DynamicDatabaseWS dd = new DynamicDatabaseWS();
            dd.updateDatabase(freeRecords, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    //Websocket: parse the page & update the database
    public void generateReport() throws IOException {
        //String content = "http://twiki.us.oracle.com/bin/view/ISVeProjects/IsveKylinProjectsFY14";
        String tableName = "table2"; //html table
        int rows = 120;
        
        sendUpdate("STATUS:Parsing page:"+content);
        parsePage(content, tableName, 120);
        
        sendUpdate("STATUS:Updating database...");
        updateDatabase();
    }

}
