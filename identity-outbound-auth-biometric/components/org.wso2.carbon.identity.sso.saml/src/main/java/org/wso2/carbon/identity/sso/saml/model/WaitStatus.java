package org.wso2.carbon.identity.sso.saml.model;
import java.io.Serializable;

public class WaitStatus implements Serializable {

    public enum Status {
        WAITING, COMPLETED, UNKNOWN
    }

    private Status status;

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }
}
