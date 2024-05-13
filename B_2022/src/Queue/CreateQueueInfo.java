package Queue;

import java.util.List;

public class CreateQueueInfo {
	private int inputQueueCount;
	private List<String> inputQueueURIs;
	private String outputQueueURI;
	
	public int getInputQueueCount() {
		return inputQueueCount;
	}
	public void setInputQueueCount(int inputQueueCount) {
		this.inputQueueCount = inputQueueCount;
	}
	public List<String> getInputQueueURIs() {
		return inputQueueURIs;
	}
	public void setInputQueueURIs(List<String> inputQueueURIs) {
		this.inputQueueURIs = inputQueueURIs;
	}
	public String getOutputQueueURI() {
		return outputQueueURI;
	}
	public void setOutputQueueURI(String outputQueueURI) {
		this.outputQueueURI = outputQueueURI;
	}
	
	@Override
	public String toString() {
		return "CreateQueueInfo [inputQueueCount=" + inputQueueCount + ", inputQueueURIs=" + inputQueueURIs
				+ ", outputQueueURI=" + outputQueueURI + "]";
	}
}
