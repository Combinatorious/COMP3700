public class CustomerModel {

    public static final String[] COL_NAMES = {"CustomerID", "Name", "Email", "Phone", "Address", "Payment Info"};

    int customerID;
    String name;
    String email;
    String phone;
    String address;
    String paymentInfo;

    public CustomerModel(int customerID, String name, String email, String phone, String address, String paymentInfo) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.paymentInfo = paymentInfo;
    }
}
