package controllers;

import java.util.List;

import models.Shop;
import models.User;
import play.mvc.Controller;

public class ShopManage extends Controller {
	public static void index() {
		List<Shop> shopList=Shop.findAll();
		render(shopList);
	}
	
	public static void editPage(Integer id) {
		Shop info = Shop.findById(id);
		render(info);
	}
	public static void deleteStop(Long id) {
		int success = models.ShopManage.deleteInfo(id);
		renderJSON(success);
	}
	public static void addShop(Shop shop) {

		boolean success = shop.save().isPersistent();
		renderJSON(success);
	}
	public static void updateShop(Shop shop) {
		
		boolean success = shop.save().isPersistent();
		renderJSON(success);
	}

	/**
	 * 姓名是否存在
	 * 
	 * @param username
	 * @param id
	 * @return
	 */
	public static boolean nameIsExist(String name, String id) {
		// false 表示已经存在
		boolean b = models.ShopManage.nameIsExist(name, id);
		return b;
	}
}
