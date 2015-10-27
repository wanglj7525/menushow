package models;

import java.util.List;

public class UserManage {

	public static List<User> allInfos() {
		return User.find("order by id desc").fetch();
	}

}
