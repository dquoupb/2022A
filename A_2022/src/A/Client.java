package A;

// 사용 X
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

public class MyClient {

	public static void main(String[] args) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		ContentResponse contestnRes = httpClient.newRequest("http://127.0.0.1:8080/helloworld").method(HttpMethod.GET).send();
		System.out.println(contestnRes.getContentAsString());

	}

}

