package financial.person;

import financial.bank.Bank;

public class Customer {

	private String name;
	private int birthYear;
	private Bank bank;
	private int amount;
	
	private Customer(String name, int birthYear, Bank bank) {
		this.name = name;
		this.birthYear = birthYear;
		this.bank = bank;
		this.amount = 0;
	}
	
	public static Customer makeCustomer(String name, int birthYear, String bankName) {
		Customer customer = null;
		Bank typeOfBank = null;
		boolean isNameValid = true, isBirthYearValid = true, isBankNameValid = true;
		if(name.equals(name.trim())) {
			String[] result = name.split("\\ ");
			if(result.length >= 2 && result.length <= 4) {
				for(String namePart : result) {
					if(namePart.length() >= 3 && Character.isUpperCase(namePart.charAt(0))) {
						for(int i = 1; i < namePart.length(); i++) {
							if(!Character.isLowerCase(namePart.charAt(i))) {
                            	isNameValid = false;
                        	}
						}
					}
					else {
						isNameValid = false;
					}
				}
			}
			else {
				isNameValid = false;
			}
		} 
		else {
			isNameValid = false;
		}
		
		if(birthYear < 1918 || birthYear > 1998) {
			isBirthYearValid = false;
		}
		
		try{
			typeOfBank = Bank.valueOf(bankName);
		} catch(IllegalArgumentException e) {
			isBankNameValid = false;
		}
		
		if(isNameValid && isBirthYearValid && isBankNameValid) {
			customer = new Customer(name, birthYear, typeOfBank);
		}
		return customer;
	}
	
	public String getName() {
		return name;
	}
	
	public Bank getBank() {
		return bank;
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
	
	@Override
	public String toString() {
		return name + ": " + amount;
	}
	
}