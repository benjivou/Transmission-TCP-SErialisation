package model;

public class Message {
	private String content; // the message content
	private boolean isLast; // true mine that the last mesage so disconnect
	
	public Message(String str, boolean isLast) {
		this.content = str; 
		this.isLast = isLast;
	}
	
	public Message(String str) {
		this.content = str;
		this.isLast = false;
	}
	
	public boolean shouldIGo() {
		return this.isLast;
	}
	
	public String getContent() {
		return this.content;
	}
}
