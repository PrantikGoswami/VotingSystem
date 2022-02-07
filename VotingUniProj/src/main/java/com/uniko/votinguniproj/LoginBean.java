/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniko.votinguniproj;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * @author Usman Ahmad
 */
@SessionScoped
@Named
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 7755587737462676166L;

    private static final Logger LOG = Logger.getLogger(LoginBean.class.getName());

    @PostConstruct
    public void newSession() {
        LOG.info("Contacts: NEW SESSION");
    }

    public boolean isLoggedIn() {
        return getUserName() != null;
    }

    public boolean isAdmin() {
        if (!isLoggedIn()) {
            return false;
        }
        return FacesContext.getCurrentInstance()
                .getExternalContext().isUserInRole("ADMIN");
    }

    private Principal oldPrincipal = null;

    public String getUserName() {
        Principal p = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        if (p != null && !p.equals(oldPrincipal)) {
            LOG.log(Level.INFO, "Contacts: LOGIN user {0}", p.getName());
        }
        oldPrincipal = p;
        return p != null ? p.getName() : null;
    }

    public void logout() throws IOException{
            Principal p = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getUserPrincipal();
            if (p != null) {
                LOG.log(Level.INFO, "Contacts: LOGOUT user {0}", p.getName());
            }
            FacesContext.getCurrentInstance().responseComplete();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.invalidateSession();
            ec.redirect(ec.getRequestContextPath());
        }
}
