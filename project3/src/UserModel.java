public class UserModel {
    public static final int CUSTOMER = 0;
    public static final int CASHIER = 1;
    public static final int MANAGER = 2;
    public static final int ADMIN = 3;

    public static final String[] COL_NAMES = {"Username", "Password", "User Type", "Customer ID"};

    public String username, password;
    public int userType;
    public int ssid; // transient value
    public int customerID;

    public UserModel() {
        // default construct
    }

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

    public static UserModel getUserFromStringArray(String[] values) {
        if (values == null || values.length != 4) {
            return null;
        }
        return new UserModel(
                values[0],
                values[1],
                Integer.parseInt(values[2]),
                Integer.parseInt(values[3])
        );
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UserModel)) {
            return false;
        }
        if ((username == ((UserModel) obj).username)
                && (password == ((UserModel) obj).password)
                && (userType == ((UserModel) obj).userType)
                && (customerID == ((UserModel) obj).customerID)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (username + password).hashCode();
    }
}
