package models;

import static javax.persistence.GenerationType.AUTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import play.db.jpa.GenericModel;

@Entity(name = "t_user")
public class User extends GenericModel {
	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(nullable = false, name = "id")
	public Integer id;
	public String u_name;
	public String u_pass;
	public int Confirmed=0;
	public Long Shopid;
	public double Nf;
	@Transient
	public String shopname;


}
