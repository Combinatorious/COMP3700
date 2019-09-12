public class CheckingAccount extends Account {
    private static final double OVERDRAFT_FEE = 15.00;

    CheckingAccount(String name) {
        super(name);
    }

    public void withdraw(double amount) {
        this.balance -= amount;
        if (this.getBalance() < 0) {
            this.balance -= OVERDRAFT_FEE;
            System.out.println(this + " has been charged an overdraft fee of " + OVERDRAFT_FEE);
        }
        System.out.println("Successfully withdrew " + amount + " from " + this);
    }
}
