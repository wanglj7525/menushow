package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.jpa.GenericModel;
@Entity
public class t_user  extends GenericModel{
	@Id
	@GeneratedValue
	@Column(nullable = false, name = "id")
	public Integer id;
	public String u_name;
	public String u_pass;
	public int Confirmed;
	public Long Shopid;
	public double Nf;

}
