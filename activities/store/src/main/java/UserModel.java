public class UserModel {
    public static final int CUSTOMER = 0;
    public static final int CASHIER = 1;
    public static final int MANAGER = 2;
    public static final int ADMIN = 3;

    public String username, password;
    public int userType;
    public int ssid;
    public int customerID;

    public UserModel(String username, String password, int userType, int customerID, int ssid) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.customerID = customerID;
        this.ssid = ssid;
    }

    /* For constructing users without customerID or SSID values just default initialize to 0 */
    public UserModel(String username, String password, int userType, int customerID) {
        this(username, password, userType, customerID, 0);
    }

    public UserModel(String username, String password, int userType) {
        this(username, password, userType, 0, 0);
    }

}
