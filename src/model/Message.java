package model;

public class Message {
	private String content; // the message content
	private boolean isLast; // true mine that the last mesage so disconnect
	private long timestamp;
	public Message(String str, boolean isLast) {
		this.content = str; 
		this.isLast = isLast;
		this.timestamp = System.currentTimeMillis();
	}
	
	public Message(String str) {
		this.content = str;
		this.isLast = true;
	}
	
	public boolean shouldIGo() {
		return this.isLast;
	}
	
	public String getContent() {
		return this.content;
	}

	public boolean getIsLast(){
	    return this.isLast;
    }

    public long getTimestamp(){
		return this.timestamp;
	}
	@Override
	public String toString() {
		return "Message{" +
				"content='" + content + '\'' +
				", isLast=" + isLast +
				'}';
	}
}
