/*
    Sample driver that creates each account and manipulates.
 */

public class Application {
    public static void main(String[] args) {
        CheckingAccount checkingTest = new CheckingAccount("Jim's Checking");

        SavingsAccount savingsTest = new SavingsAccount("Jim's Savings");

        System.out.println("Depositing $" + 100.00 + " into " + savingsTest);
        savingsTest.deposit(100.00);
        System.out.println("Withdrawing $" + 200.00 + " from " + savingsTest);
        savingsTest.withdraw(200.00);
        for (int i = 0; i < SavingsAccount.WITHDRAW_COUNT_LIMIT; i++) {
            System.out.println("Withdrawing $" + 1.00 + " from " + savingsTest);
            savingsTest.withdraw(1.00);
        }
        System.out.println("Withdrawing $" + 1.00 + " from " + savingsTest);
        savingsTest.withdraw(1.00);

        System.out.println("Depositing $" + 50.00 + " into " + checkingTest);
        checkingTest.deposit(50.00);
        System.out.println("Withdrawing $" + 60.00 + " from " + checkingTest);
        checkingTest.withdraw(60.00);
    }
}
