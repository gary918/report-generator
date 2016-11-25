/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator.struts2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.ff.reportgenerator.Delegate;

/**
 *
 * @author gary
 */
public class UpdateDataActionSupport extends ActionSupport {

    private int fileNumber = 0;
    private int totalValue = 0;
    
    Delegate dg = null;

    public int getFileNumber() {
        fileNumber = fileNumber + 30;
        return fileNumber;
    }

    public UpdateDataActionSupport() {
        Delegate dg = new Delegate();
    }

    public String execute() throws Exception {
        //new Thread().
        Thread.sleep(5000);
        fileNumber++;

        System.out.println("calling..." + fileNumber);
        return SUCCESS;

    }

    public void getProgressValueByJson() {
        int actualValue = 0;
        int progressValue = 0;  // progressValue = 100*actualValue/totalValue
        if(dg == null){
            dg = new Delegate();
        }

        /*
        String totalValueString = getCookie(getRequest(), "totalValue");
        if (totalValueString != null) {
            totalValue = Integer.parseInt(totalValueString);
        }*/
        if (totalValue == 0) {
            dg.generateReport();
            totalValue = dg.countFreeRecords();
        }

        //actualValue = dg.countTable();
        progressValue = 100 * actualValue / totalValue;

        System.out.println("total=" + totalValue + ";actual=" + actualValue);
        writeJsonString("{\"progressValue\":\"" + progressValue + "\"}");
        setCookie(getResponse(), "totalValue", totalValue + "", 365 * 24 * 60 * 60);
    }

    /**
     * Get HttpServletRequest Object
     *
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * Get HttpServletResponse Object
     *
     * @return HttpServletResponse
     */
    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * Get PrintWriter Object
     *
     * @return PrintWriter
     * @throws IOException
     */
    protected PrintWriter getWriter() throws IOException {
        return this.getResponse().getWriter();
    }

    /**
     * 写Json格式字符串
     *
     * @param json
     */
    protected void writeJsonString(String json) {
        try {
            getResponse().setContentType("text/html;charset=UTF-8");
            this.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param name
     * @return String
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String value = null;
        try {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(name)) {
                    value = c.getValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     * @param value
     * @param period
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int period) {
        try {
            Cookie div = new Cookie(name, value);
            div.setMaxAge(period);
            response.addCookie(div);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
