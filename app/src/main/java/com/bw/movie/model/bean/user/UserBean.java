package com.bw.movie.model.bean.user;

/**

 * function:
 */
public class UserBean {
    private int userId;
    private String sessionId;

    public UserBean(int userId, String sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public UserBean() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
