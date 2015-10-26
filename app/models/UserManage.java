package models;

import java.util.List;

public class UserManage {

	public static List<t_user> allInfos() {
		return t_user.find("order by id desc").fetch();
	}

}
