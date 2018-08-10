public class CurrentUserInfo {
    private static CurrentUserInfo singleton = new CurrentUserInfo();

    static CurrentUserInfo getInstance() {
        return singleton;
    }

    private String email;

    void setUser(String email) {
        this.email = email;
    }

    String getUser() {
        return email;
    }

    boolean getAdminStatus() {
        return DatabaseInterfacer.getInstance().checkAdminStatus(email);
    }
    boolean getCuratorStatus() {
        return DatabaseInterfacer.getInstance().checkGeneralCuratorStatus(email);
    }
}