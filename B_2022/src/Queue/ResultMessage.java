package Queue;


import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lgcns.test.http.RestClient;
import com.lgcns.test.queue.CommandMessage;
import com.lgcns.test.queue.CreateQueueInfo;
import com.lgcns.test.queue.ResultMessage;

public class RunManager {

	private static String controllerUri = "http://127.0.0.1:8080/queueInfo";
	
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static void main(String[] args) throws Exception {
		RestClient restClient = new RestClient();
		ContentResponse response = restClient.callUrl(controllerUri, HttpMethod.GET, null, new HttpClient());
		CreateQueueInfo queueInfo = gson.fromJson(response.getContentAsString(), CreateQueueInfo.class);
		
		Worker[] worker = new Worker[queueInfo.getInputQueueCount()]; 
		for (int i = 0; i < queueInfo.getInputQueueCount(); i++) {
			worker[i] = new Worker(i);
			final int idx = i;
			
			Thread t = new Thread(() -> {
				HttpClient input = new HttpClient();
				HttpClient output = new HttpClient();
				while (true) {
					try {
						ContentResponse res = restClient.callUrl(queueInfo.getInputQueueURIs().get(idx), HttpMethod.GET, null, input);
						CommandMessage msg = gson.fromJson(res.getContentAsString(), CommandMessage.class);
						System.out.println(msg);
						String result = worker[idx].run(msg.getTimestamp(), msg.getValue());
						System.out.println(result);
						ResultMessage resultMsg = new ResultMessage();
						resultMsg.setResult(result);
						if (result != null && result.length() > 0) {
							restClient.callUrl(queueInfo.getOutputQueueURI(), HttpMethod.POST, gson.toJson(resultMsg), output);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}

}
