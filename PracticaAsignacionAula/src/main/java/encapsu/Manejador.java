package encapsu;

import java.util.HashMap;
import java.util.Map;

import io.javalin.Javalin;

public class Manejador {
	
	public Manejador() {
		super();
	}
	public void rutas(Javalin app, Usuario e){
		Map<String, String> info = new HashMap<String, String>() ;
		app.get("/", ctx -> ctx.redirect("/Inicio.html"));
			app.config.accessManager((handler, ctx, permittedRoles) -> {
				Usuario exa = new Usuario();
				app.post("/loggear", ctx -> {
					info.put(ctx.formParam("username"), ctx.formParam("password"));
				});
				app.get("/fin", ctx ->{
					
				});
				if(comparar(e, exa)) {
					ctx.redirect("/AccesoDenegao.html");
				}else {
					ctx.redirect("/AccesoDenegao.html");
				}
			});
		
	}
	
	private boolean comparar(Usuario e, Usuario exa) {
		if(e.getUser().equalsIgnoreCase(exa.getUser()))
			if(e.getPassword().contentEquals(exa.getPassword()))
				return true;
		return false;
	}
}
