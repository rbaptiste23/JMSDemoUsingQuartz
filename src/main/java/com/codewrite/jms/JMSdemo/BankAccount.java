package com.codewrite.jms.JMSdemo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BankAccount implements Serializable {
private static final long serialVersionUID = 1551360162035742724L;
	
	
	
	private final String id;
	private int balance =0;


	private Object os;
	
	


	public Object getOs() {
		return os;
	}

	public void setOs(Object os) {
		this.os = os;
	}

	public BankAccount(String id) {
		this.id = id;
	}
	
	public BankAccount(String id, int balance) {
		this.id = id;
		this.balance = balance;
	}
	
	public void deposit(int bal ) {
		balance+=bal;
		
	}
	
	public void printBalance() {
		System.out.println("The balance is: " + balance);
	}
	
	

		

	
	
	public BankAccount loadAccount(String fileName) {
		BankAccount ba = null;
		
		try(ObjectInputStream objectStream =  new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
			ba = (BankAccount) objectStream.readObject(); 
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			
		}
		return ba;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getId() {
		return id;
	}
	

}
