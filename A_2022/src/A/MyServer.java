package A;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class MyServer {

	Server server;
	Proxy proxy;
	
	public MyServer(Proxy proxy) throws Exception {
		// 서버 띄우기
		server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(proxy.getPort());
		server.addConnector(http);
		
		// 객체를 가져와야할 때는 servletholder를 사용하는
		MyServlet myServlet = new MyServlet(proxy);
		ServletHolder holder = new ServletHolder(myServlet);
		
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(holder, "/*");
//		servletHandler.addServletWithMapping(MyServlet.class, "/*");
		server.setHandler(servletHandler);
		
		server.start();
		server.join();
	}
	
	
}
