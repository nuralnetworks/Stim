package models;

public class Player extends User {
    private double walletBalance;
    private Library library;

    public Player(String username, String password, double initialBalance) {
        super(username, password);
        this.walletBalance = initialBalance;
        this.library = new Library();
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void addBalance(double amount) {
        this.walletBalance += amount;
    }

    public boolean deductBalance(double amount) {
        if (walletBalance >= amount) {
            walletBalance -= amount;
            return true;
        }
        return false;
    }

    public Library getLibrary() {
        return library;
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Player");
    }
}
