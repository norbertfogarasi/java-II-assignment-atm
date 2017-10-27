package financial.bank;

public class ATM {

	private Bank bank;
	private int amount;
	
	private ATM(Bank bank, int amount) {
		this.bank = bank;
		this.amount = amount;
	}
	
	public static ATM makeATM(String bankName, int amount) {
		ATM atm = null;
		Bank typeOfBank = null;
		boolean isBankNameValid = true;
		try{
			typeOfBank = Bank.valueOf(bankName);
		} catch(IllegalArgumentException e) {
			isBankNameValid = false;
		}
		
		if(isBankNameValid && amount > 0) {
			atm = new ATM(typeOfBank, amount);
		}
		return atm;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void decreaseAmount(int decAmount) {
		amount -= decAmount;
	}
	
	public void increaseAmount(int incAmount) {
		amount += incAmount;
	}
	
	public int calculateFee(Bank bank, int value) {
		int fee = 0;
		if(bank == this.bank) {
			fee = (int)Math.ceil(value * 0.01);
			if(fee < 200) {
				fee = 200;
			}
		}
		else {
			fee = (int)Math.ceil(value * 0.03);
			if(fee < 500) {
				fee = 500;
			}
		}
		return fee;
 	}
}