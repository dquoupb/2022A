package A;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;

public class MyServlet extends HttpServlet {
	private Proxy proxy;
	
	public MyServlet(Proxy proxy) {
		this.proxy = proxy;
	}

	protected void doCommon(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("===================================");
		System.out.println("PROXY : "+ req.getRequestURL());
		System.out.println("===================================");

		String pathPrefix = req.getRequestURI(); // /front
		String[] url = req.getRequestURL().toString().split(pathPrefix); // http://127.0.0.1:5001
		String query = req.getQueryString();
		
		System.out.println("path prefix : "+pathPrefix);
		System.out.println("URL : "+url[0]);
		System.out.println("query : "+query);
		
		String body = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(Collectors.joining());
		System.out.println(body);
		String input = "/"+req.getRequestURI().split("/")[1];
		
		for(Route route : proxy.getRoutes()) {
			if(input.equals(route.getPathPrefix())) { 
				query = req.getQueryString() == null ? "" : "?" + req.getQueryString();
				HttpClient httpClient = new HttpClient();
				httpClient.start();
				
				System.out.println("client호출 : "+route.getUrl()+req.getRequestURI()+query);
				Request request = httpClient.newRequest(route.getUrl()+req.getRequestURI()+query).method(req.getMethod());
				if(body != null) {
					request.content(new StringContentProvider(body));
				}
				ContentResponse contentRes = request.send();
				
				System.out.println("responseString=[" + contentRes.getContentAsString() + "]");
//				res.setContentType(contentRes.getMediaType());
//				res.setStatus(contentRes.getStatus());
				res.getWriter().write(contentRes.getContentAsString());
				
				break;
			}
		}
	}
		
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            doCommon(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            doCommon(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        
}