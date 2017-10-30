package financial;

import financial.bank.ATM;
import financial.person.Customer;
import java.util.ArrayList;
import java.io.*;

public class Simulator {

	private ATM atm;
	private ArrayList<Customer> customers;
	private PrintWriter pwLog;
	
	public Simulator(String bankName, int initAmount, String outputFileName) throws FileNotFoundException {
		this.atm = ATM.makeATM(bankName, initAmount);
		if(this.atm != null) {
			customers = new ArrayList<>();
			pwLog = new PrintWriter(new File(outputFileName));
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private Customer getCustomerByName(String customerName) {
		for(Customer c : customers) {
			if(c.getName().equals(customerName)) {
				return c;
			}
		}
		return null;
	}
	
	public void insertCustomer(String customerName, int birthYear, String bankName) {
		if(getCustomerByName(customerName) == null) {
			Customer c = Customer.makeCustomer(customerName, birthYear, bankName);
			if(c != null) {
				customers.add(c);
			}
		}
	}
	
	public void withdrawCash(String customerName, int amount) {
		Customer c = getCustomerByName(customerName);
		if(c != null && atm.getAmount() >= amount && amount > 0) {
			int fee = atm.calculateFee(c.getBank(), amount);
			if(c.getAmount() >= (fee + amount)) {
				c.decreaseAmount(fee + amount);
				atm.decreaseAmount(amount);
				pwLog.println(c.toString());
			}
		}
	}
	
	public void depositCash(String customerName, int amount) {
		Customer c = getCustomerByName(customerName);
		if(c != null && amount > 0) {
			atm.increaseAmount(amount);
			c.increaseAmount(amount);
			pwLog.println(c.toString());
		}
	}
	
	public void simulate(String inputFileName) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(inputFileName)));
		String line = null;
		while((line = br.readLine()) != null) {
			String[] result = line.split(":");
			if(result.length != 2) {
				continue;
			}
			String[] data = result[1].split(",");
			if(result[0].equals("REG")) {
				if(data.length != 3) {
					continue;
				}
				int birthYear = 0;
				try {
					birthYear = Integer.parseInt(data[1]);
					insertCustomer(data[0], birthYear, data[2]);
				} catch(NumberFormatException e) { }
			}
			else if(result[0].equals("GET")) {
				if(data.length != 2) {
					continue;
				}
				int reqAmount = 0;
				try {
					reqAmount = Integer.parseInt(data[1]);
					withdrawCash(data[0], reqAmount);
				} catch (NumberFormatException e) { }
			}
			else if(result[0].equals("PUT")) {
				if(data.length != 2) {
					continue;
				}
				int putAmount = 0;
				try {
					putAmount = Integer.parseInt(data[1]);
					depositCash(data[0], putAmount);
				} catch (NumberFormatException e) { }
			}
		}
	}
	
	public void close() { 
		pwLog.close();
	}
}