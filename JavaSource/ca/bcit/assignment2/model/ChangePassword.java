package ca.bcit.assignment2.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("newPass")
@RequestScoped

/**
 * bean that will help with changing passwords
 *
 */
public class ChangePassword {
    /**
     * field for old password
     */
    private String oldPassword;
    
    /**
     * field for new password
     */
    private String newPassword;
    
    /**
     * field for new password repeated
     */
    private String repeatPassword;

    /**
     * getter for oldPassword
     * @return
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Setter for oldPasswrod
     * @param oldPassword
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * getter for newPassword
     * @return
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * getter for newPassword
     * @return
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * getter for repeatPassword
     * @return
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * getter for repeatPassword
     * @return
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
