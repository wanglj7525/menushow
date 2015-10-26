package controllers;

import play.mvc.*;

public class Home extends Controller {

    public static void index() {
    	String username=session.get("name");
        render(username);
    }

}
