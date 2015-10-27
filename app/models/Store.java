package models;

import static javax.persistence.GenerationType.AUTO;

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
	public int ExpiredDate;

}
