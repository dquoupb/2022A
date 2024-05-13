package Queue;

public class CommandMessage {
	private long timestamp;
	private String value;
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "CommandMessage [timestamp=" + timestamp + ", value=" + value + "]";
	}
}
