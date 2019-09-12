public class Account {

    double balance;
    private String name;

    Account(String name) {
        this.balance = 0;
        this.name = name;
    }

    double getBalance() {
        return this.balance;
    }
    String getName() {
        return this.name;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
        System.out.println("Successfully withdrew " + amount + " from " + this);
    }

    public String toString() {
        return this.getName() + " ($" + this.getBalance() + ")";
    }
}
