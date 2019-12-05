/*
 Wrapper on JSON data that includes a server communication code
 */

public class MessageModel {

    /*
    Client-server communication codes
     */
    public static final int GET_PRODUCT = 100;
    public static final int PUT_PRODUCT = 101;

    public static final int GET_CUSTOMER = 200;
    public static final int PUT_CUSTOMER = 201;

    public static final int GET_PURCHASE = 300;
    public static final int PUT_PURCHASE = 69;

    public static final int GET_USER = 400;
    public static final int PUT_USER = 401;
    public static final int REMOVE_USER = 402;

    public static final int LOGIN  = 10;
    public static final int LOGOUT = 11;

    public static final int GET_ALL_PRODUCTS = 500;
    public static final int GET_ALL_CUSTOMERS = 501;
    public static final int GET_ALL_PURCHASES = 502;
    public static final int GET_ALL_USERS = 503;

    public static final int UPDATE_VALUE = 1000;
    public static final int DELETE_ROW = 1001;

    public static final int ERROR = 0;
    public static final int SUCCESS = 1;

    // operation code
    public int code;
    // return code
    public int ssid;
    // serialized data
    public String data;
}
