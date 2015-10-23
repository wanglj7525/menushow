package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity
public class t_store  extends GenericModel{
	@Id
	@GeneratedValue
	@Column(nullable = false, name = "id")
	public Integer id;
	public String s_name;
	public String dog_id;
	public Long u_id;
	public int ExpiredDate;

}
