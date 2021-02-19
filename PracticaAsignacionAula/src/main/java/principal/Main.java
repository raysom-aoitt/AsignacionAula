package principal;

import java.lang.reflect.Method;

import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpContext;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import encapsu.Manejador;
import encapsu.Usuario;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

public class Main {

	public static void main(String[] args) throws Exception {
		Usuario e = new Usuario("admin","admin");
		Javalin app = Javalin.create(config->{
			config.addStaticFiles("/recursoshtml");
			config.registerPlugin(new RouteOverviewPlugin("/rutas"));
			config.enableCorsForAllOrigins();
		});
		Server servidor = app.server().server();
		ContextHandlerCollection Handler = new ContextHandlerCollection();
		servidor.setHandler(Handler);
		SetEndpoints(app, servidor);
		app.start(7003);
		
		new Manejador().rutas(app, e);
	}

	private static void SetEndpoints(Javalin app, Server servidor) throws Exception {
		JettyHttpContext contexto = build(servidor, "/loggin");
		
	}
	
	private static JettyHttpContext build(Server servidor, String relativepath) throws Exception {
		 JettyHttpServer jettyHttpServer = new JettyHttpServer(servidor, true);
	     JettyHttpContext ctx = (JettyHttpContext) jettyHttpServer.createContext(relativepath);
	     Method method = JettyHttpContext.class.getDeclaredMethod("getJettyContextHandler");
	     method.setAccessible(true);
	     HttpSpiContextHandler contextHandler = (HttpSpiContextHandler) method.invoke(ctx);
	     contextHandler.start();
	     return ctx;
	}
}
