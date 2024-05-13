package Http;


import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;

public class RestClient {
	//public HttpClient httpClient;

    public RestClient() {
        //this.httpClient = new HttpClient();
    }

    public ContentResponse callUrl(String url, HttpMethod httpMethod, String data, HttpClient httpClient) throws Exception {
        if (!httpClient.isStarted())
            httpClient.start();
        Request request = httpClient.newRequest(url).method(httpMethod);
        request.header(HttpHeader.CONTENT_TYPE, "application/json");
        request.header(HttpHeader.ACCEPT_CHARSET, "UTF-8");
        request.header(HttpHeader.CONTENT_ENCODING, "UTF-8");
        if (data != null)
        	request.content(new StringContentProvider(data));
        return request.send();
    }
}
