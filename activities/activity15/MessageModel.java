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
    public static final int PUT_PURCHASE = 301;

    public static final int ERROR = 0;
    public static final int SUCCESS = 1;

    // operation code
    public int code;
    // serialized data
    public String data;
}
