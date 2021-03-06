/*
 *  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */

package org.wso2.carbon.extension.identity.authenticator;

import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.mvel2.debug.Debugger;
import org.wso2.carbon.core.services.authentication.AbstractAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.AbstractApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.config.ConfigurationFacade;
import org.wso2.carbon.identity.application.authentication.framework.config.builder.FileBasedConfigurationBuilder;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.AuthenticationFailedException;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.application.authenticator.oidc.OIDCAuthenticatorConstants;
import org.wso2.carbon.identity.application.authenticator.oidc.OpenIDConnectAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.FederatedApplicationAuthenticator;
import org.wso2.carbon.identity.application.common.model.Property;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.sso.saml.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * Authenticator of biometric
 */

public class biometricAuthenticator extends AbstractApplicationAuthenticator
        implements FederatedApplicationAuthenticator {


    private static final Log log = LogFactory.getLog(biometricAuthenticator.class);
    public static String MySDK;
    public static String randomChallenge;
    public static String encryptedChallenge;
    public static String user;



    /**
     * Get the friendly name of the Authenticator
     */
    @Override
    public String getFriendlyName() {
        return biometricAuthenticatorConstants.AUTHENTICATOR_FRIENDLY_NAME;
    }

    @Override
    public boolean canHandle(HttpServletRequest httpServletRequest) {
        encryptedChallenge = httpServletRequest.getParameter(biometricAuthenticatorConstants.ENCRYPTED_CHALLENGE);
        System.out.println("encrypted challenge from the form submitted: "+encryptedChallenge);
        //return encryptedChallenge != null;
        //return false;
       // challenge is present-true
        //or else
        if (encryptedChallenge==null) {
            System.out.println("this is false branch");
            return false;
        }
        else {
            System.out.println("this is true branch");
            return true;
        }

    }




    @Override
    public String getContextIdentifier(javax.servlet.http.HttpServletRequest httpServletRequest) {
         MySDK = httpServletRequest.getParameter("sessionDataKey");
        System.out.println("My autogenerated Session Data Key is : "+ MySDK);
        log.info("session data key printed in log info is: "+ MySDK);
        //log.debug("session data key printed in log debug is: "+ MySDK);
        return httpServletRequest.getParameter("sessionDataKey");
    }



    /**
     * Get the name of the Authenticator
     */
    @Override
    public String getName() {
        return biometricAuthenticatorConstants.AUTHENTICATOR_NAME;
    }

    protected String getTokenEndpoint(Map<String, String> authenticatorProperties) {
        return authenticatorProperties.get(biometricAuthenticatorConstants.SERVER_KEY);
    }

    @Override
    protected void initiateAuthenticationRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationContext context) throws AuthenticationFailedException {

        AuthenticatedUser user = context.getSequenceConfig().getStepMap().get(1).getAuthenticatedUser();

        String DBusername = user.getUserName();


        Map<String, String> authenticatorProperties = context.getAuthenticatorProperties();
        String FirebaseSK = authenticatorProperties.get(biometricAuthenticatorConstants.SERVER_KEY);
        String tokenEndPoint = getTokenEndpoint(authenticatorProperties);




        List<String> putIds;


        //String server_key ="AAAAJp0GZ-I:APA91bHfqf4eWjGbQSIPE6o_gEduOTPhEeOEdoVu5sEN106kbI30-YY8J9ySsh0Vj3QDCd7ovqjVlhyMuEkuLapQNYe6Ta7of0aT4FFAOLqZ3E_FrbSZWhn5kyypaRV-bFHS34Falvnn" ;
        String message = "Authenticate with your fingerprint44";
        String server_key = FirebaseSK;


        UUID challenge = UUID.randomUUID();//challenge debug log
        randomChallenge = challenge.toString();
        System.out.println("Random Challenge is  = " + randomChallenge);
        //log.debug("this is my randomly generated challenge1: " + randomChallenge);
        log.info("this is my randomly generated challenge2: " + randomChallenge);




        HashMap<String, String> tokenList = new HashMap<>();

        tokenList.put("dewni", "ca0OWsg-30c:APA91bHQj3LRdOt7BgQJsOGbz_uWBFe8BtBLS0WSXZFNnVj6BZkU7PM__bHuoZXE6z_mzsjdZaKMerHaILfsf2jalgpndp67b5Vt9xvG0lODmksCq-Nk5N8pdIv1DRHJkVZGKygFcnmw");
        tokenList.put("chamodi", "cMXgOC6PIFA:APA91bHbV-ZT6JJnqPtt_0jCtImNFtEFefhY3FAck83eZkUBnRmUevrgLbo3G1iC8Xf8dpH3mQ0kVhnJTHnoX_8UocAc5Y1-zbEbTUYQMdFNFruT77KX8skRRF7XY_X8C4Wlo3nZmWAu");
        tokenList.put("yasara","fQelT6DTlF4:APA91bEnMJ1dWYSFJak2VkOTxzIHekV1mOVQ3XhUeb6lWUrR5W0s-LYx7lDfQR3J5GEnPmLt-YM-NqsRq-JvD8rqzt2bMLVGb510v9nw_dzx-qbyb2oXt4wGpfRx_mawA1kQ3i0Tb_vh");
        String tokenId = tokenList.get(DBusername);


        putIds = new ArrayList();
        //putIds.add(tokenId1);
        putIds.add(tokenId);


        /*for individual*/
        PushNotif.send_FCM_Notification(tokenId, server_key, message, randomChallenge, MySDK);

        try {
            //String loginPage = ConfigurationFacade.getInstance().getAuthenticationEndpointURL();
            //String redirectURL = "https://localhost:9443/authenticationendpoint/wait.do";
            String waitPage= "https://localhost:9443/authenticationendpoint/wait.jsp?sdk="+MySDK;

            //String redirectURL = loginPage;
            response.sendRedirect(waitPage);
        } catch (IOException e) {
            e.printStackTrace();//remove this.
        }


    }

    /**
     * Process the response of the SMSOTP end-point.
     *
     * @param httpServletRequest  the HttpServletRequest
     * @param httpServletResponse the HttpServletResponse
     * @param authenticationContext  the AuthenticationContext
     * @throws AuthenticationFailedException
     */

    @Override
    protected void processAuthenticationResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationContext authenticationContext) throws AuthenticationFailedException {

        System.out.println("encrypted challenge from the form--->now inside processAuth method: "+encryptedChallenge);
        System.out.println(randomChallenge+"     AAANNNNDDDD   "+encryptedChallenge);
        if (randomChallenge.equals(encryptedChallenge)  ){
            System.out.println("Final Step!! ");
            AuthenticatedUser user = authenticationContext.getSequenceConfig().getStepMap().get(1).getAuthenticatedUser();
            //AuthenticatedUser user = (AuthenticatedUser) authenticationContext.getProperty(biometricAuthenticatorConstants.AUTHENTICATED_USER);
            authenticationContext.setSubject(user);

        }
        else {
            System.out.println("2 challenges are not the same!");
        }

    }

    /**
     * Get Configuration Properties
     */
    @Override
    public List<Property> getConfigurationProperties() {
        //Add your configuration properties

        List<Property> configProperties = new ArrayList<Property>();
//        Property username = new Property();
//        username.setName(biometricAuthenticatorConstants.USERNAME);
//        username.setDisplayName("Enter the Username");
//        username.setDescription("Enter the username that is registered in WSO2 IS");
//        username.setDisplayOrder(0);
//        configProperties.add(username);

        Property serverKey = new Property();
        serverKey.setName(biometricAuthenticatorConstants.SERVER_KEY);
        serverKey.setDisplayName("Firebase Server Key");
        serverKey.setDescription("Enter the firebase server key of the android app");
        serverKey.setDisplayOrder(1);
        configProperties.add(serverKey);


        return configProperties;
    }
}