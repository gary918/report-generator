/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ff.reportgenerator.test;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//import com.ff.reportgenerator.*;
import com.ff.reportgenerator.mongodb.*;


/**
 *
 * @author gary
 */
@ServerEndpoint("/websocket")
public class WebSocketTest {

    @OnMessage
    public void onMessage(String message, Session session)
            throws IOException, InterruptedException {

        // Print the client message for testing purposes
        System.out.println("Gary Received: " + message);

        // Send the first message to the client
        session.getBasicRemote().sendText("STATUS:Start ...");
        
        //DelegateWS dg = new DelegateWS(message, session);        
        //dg.generateReportWS();
        DelegateWS dgws = new DelegateWS(message, session);
        dgws.generateReport();

        // Send a final message to the client
        session.getBasicRemote().sendText("STATUS:Data updage completed.");
    }

    @OnOpen
    public void onOpen() {
        System.out.println("Client connected");
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");

    }
    
}
