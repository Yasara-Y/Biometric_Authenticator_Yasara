package org.wso2.carbon.identity.sso.saml.javascript.flow;

import org.wso2.carbon.identity.sso.saml.model.WaitStatus;

public class WaitStatusResponse {
    private String status;
    private String challenge;



    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getChallenge(){
        return  challenge;

    }

    public void setChallenge(String challenge){

        this.challenge=challenge;
    }
}
