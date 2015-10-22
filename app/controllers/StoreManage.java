package controllers;

import play.mvc.Controller;

public class StoreManage extends Controller{

	public static void index(Long id){
		render(id);
	}
}
