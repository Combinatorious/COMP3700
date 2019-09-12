public class SavingsAccount extends Account {
    private int withdrawCount;
    private int monthlyDeposit;

    static final int WITHDRAW_COUNT_LIMIT = 3;
    private static final double MONTHLY_FEE = 2.00;
    private static final double MONTHLY_DEPOSIT_MIN_WAIVER = 50.00;

    SavingsAccount(String ownerName) {
        super(ownerName);
        withdrawCount = 0;
        monthlyDeposit = 0;
    }

    public void resetMonthlyCounts() {
        this.withdrawCount = 0;
        this.monthlyDeposit = 0;
    }

    public double issueMonthlyFee() {
        if (monthlyDeposit > MONTHLY_DEPOSIT_MIN_WAIVER) {
            return 0;
        }
        balance -= MONTHLY_FEE;
        return MONTHLY_FEE;
    }

    public int getWithdrawCount() {
        return this.withdrawCount;
    }

    public int getMonthlyDeposit() {
        return this.monthlyDeposit;
    }

    public void deposit(double amount) {
        monthlyDeposit += amount;
        super.deposit(amount);
    }

    public void withdraw(double amount) {
        if (withdrawCount == WITHDRAW_COUNT_LIMIT) {
            System.out.println("Error: account has reached monthly withdrawal limit");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: can only withdraw positive amount");
            return;
        }
        if (balance < amount) {
            System.out.println("Error: can not overdraw account.");
            return;
        }
        withdrawCount++;
        super.withdraw(amount);
    }
}
