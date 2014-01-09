package de.htw_berlin.f4.ai.kbe.kurznachrichten;

public class AuthorizationException extends Exception{

	private static final long serialVersionUID = -884980557364861116L;

	private String userName;
	
	public AuthorizationException(String userName){
		this.userName = userName;
	}
	
	@Override
	public String toString(){
		return "User " + userName + " is not authorized for this kind of operation.";
	}
	
}
