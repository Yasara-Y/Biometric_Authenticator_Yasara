package org.wso2.carbon.identity.sso.saml.servlet;
import org.wso2.carbon.identity.sso.saml.javascript.flow.WaitStatusResponse;
import org.wso2.carbon.identity.sso.saml.model.WaitStatus;
//import com.sun.xml.internal.ws.util.StringUtils;
//import sun.swing.StringUIClientPropertyKey;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import com.google.gson.Gson;
import org.wso2.carbon.identity.sso.saml.javascript.flow.WaitStatusResponse;

public class SAMLBiometricServlet extends HttpServlet {


    boolean value1 = false;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);


    }
    HashMap<String, String> updateStatus = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WaitStatusResponse waitResponse = null;
        if (request.getParameterMap().containsKey("t")) {
            String para1 = request.getParameter("t");
            //System.out.println("t parameter is : " + para1);

            waitResponse = new WaitStatusResponse();

            if (para1.equals("mobile")) {


                if ((request.getParameterMap().containsKey("SDKMobile")) && request.getParameterMap().containsKey("CHALLENGEMobile")) {
                    String sdkmobile = request.getParameter("SDKMobile");
                    String challengeMobile = request.getParameter("CHALLENGEMobile");
                    System.out.println("challenge mobile parameter is : " + challengeMobile);
                    System.out.println("sdk mobile parameter is : " + sdkmobile);
                    updateStatus.put(sdkmobile, challengeMobile);
                    System.out.println("table is: " + updateStatus);
                    response.setContentType("text/html");
                    response.setStatus(200);
                    PrintWriter out = response.getWriter();
                    System.out.println("<h3>mobile sdk and challenge received !</h3>");
                    return;
                }
            } else if (para1.equals("web")) {


                if (request.getParameterMap().containsKey("SDKWeb")) {
                    String sdkweb = request.getParameter("SDKWeb");
                    //System.out.println("sdk web parameter is : " + sdkweb);
                    String challengeExtracted = updateStatus.get(sdkweb);
                    //System.out.println("Challenge against the sdk web in the hashmap iss:  "+challengeExtracted);
                    //assert status != null;
                    if (challengeExtracted != null && updateStatus.containsKey(sdkweb)) {

                        response.setContentType("text/html");
                        response.setCharacterEncoding("utf-8");
                        response.setStatus(200);
//                        PrintWriter xy = response.getWriter();
//                        xy.println("so the challenge is: "+ challengeExtracted);
//                        xy.close();
                        //ServletContext sc = this.getServletContext();
                        //RequestDispatcher rd = sc.getRequestDispatcher("../../../../../../org.wso2.carbon.extension.identity.authenticator.biometric/src/main/resources/Artifacts/success.jsp");
                       // RequestDispatcher rd = sc.getRequestDispatcher("wait.jsp");
                        request.setAttribute("challenge", challengeExtracted);
                        //rd.forward(request,response);
                        waitResponse.setStatus(WaitStatus.Status.COMPLETED.name());
                        waitResponse.setChallenge(challengeExtracted);
                        updateStatus.remove(sdkweb);
                        //System.out.println(waitResponse.setChallenge(challengeExtracted));
                        System.out.println("updated table now: " + updateStatus);
                        System.out.println("a response has arrived from the mobile device!!!");
                        //response.sendRedirect("https://localhost:9443/authenticationendpoint/success.do");

                    } else {
                        response.setContentType("text/html");
                        response.setStatus(401);
                        response.sendError(401, "blah blah ");
                        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "message goes here");
//                        PrintWriter out = response.getWriter();
//                        out.println("<h3>Please wait until the authorization process is over... !</h3>");
                        //System.out.println("<Please wait until the authorization process is over... !</h3>");

                    }
                }
            }
        } else {
            response.setContentType("text/html");
            response.setStatus(400);
            PrintWriter out = response.getWriter();
            out.println("<h3>Invalid request... !</h3>");
            System.out.println("<Please wait until the authorization process is over... !</h3>");
        }

        response.setContentType("application/json");
        String json = new Gson().toJson(waitResponse);
        try (PrintWriter out = response.getWriter()) {
            if (json.contains("status")){
                System.out.println("json waitresponse: "+json);
            }
            out.print(json);
            out.flush();
        }
    }
}
