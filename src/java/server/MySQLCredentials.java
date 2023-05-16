package server;

public class MySQLCredentials {
    private String DB_URL;
    private String USER;
    private String PASS;

    public MySQLCredentials() {

    }

    public MySQLCredentials(String DB_URL, String USER, String PASS) {
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    public void setURL(String DB_URL) {
        this.DB_URL = (this.DB_URL == null || this.DB_URL.equals("")) ? DB_URL : this.DB_URL;
    }

    public void setUSER(String USER) {
        this.USER = (this.USER == null || this.USER.equals("")) ? USER : this.USER;
    }

    public void setPASS(String PASS) {
        this.PASS = (this.PASS == null || this.PASS.equals("")) ? PASS : this.PASS;
    }

    public String getURL() {
        return DB_URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASS() {
        return PASS;
    }
}
