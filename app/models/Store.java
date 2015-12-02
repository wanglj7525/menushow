package models;

import static javax.persistence.GenerationType.AUTO;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity(name = "t_store")
public class Store  extends GenericModel{
	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(nullable = false, name = "id")
	public Integer id;
	public String s_name;
	public String dog_id;
	public Long u_id;
	public Date expiredDate=new Date();
	
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime());
		c.add(Calendar.DATE, 30);// 天后的日期
		Date date = new Date(c.getTimeInMillis()); // 将c转换成Date
		this.expiredDate = date;
	}
}
