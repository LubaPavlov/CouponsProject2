package imports.d20170427coupProjDafnaWeiss.service;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {

	private String message = "OK";

	// an empty constructor
	public Response(){
	}
	
	// constructor
	public Response(String message){
		this.message = message;
	}

	public String setMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
