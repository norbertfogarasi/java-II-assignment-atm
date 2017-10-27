package main;

import financial.person.Customer;
import financial.bank.Bank;
import financial.bank.ATM;
import financial.Simulator;
import java.io.*;

public class Main {
	
	public static void main(String[] args) {
		
		if(args.length != 3) {
			System.err.println("Invalid number of parameters!");
			System.exit(1);
		}
		
		Simulator sim = null;
		try {
			sim = new Simulator(args[0], 1000000, args[2]);
		} catch(FileNotFoundException e) {
			System.err.println("Something went wrong!");
			System.exit(1);
		} catch(IllegalArgumentException e) {
			System.err.println("Something went wrong!");
			System.exit(1);
		}
		
		try {
			sim.simulate(args[1]);
			sim.close();
		} catch (FileNotFoundException e) {
			System.err.println("The given input file wasn't found!");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("IOException occurred!");
			System.exit(1);
		}
	}
}
