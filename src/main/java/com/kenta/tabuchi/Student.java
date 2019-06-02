package com.kenta.tabuchi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="M_Student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column(length=50,nullable=false)
	@NotEmpty
	private String name;
	
	@Column(nullable=true)
	@Pattern(regexp="....[/]..[/]..")
	private String birthday;
	
	@Column(length=18,nullable=true)
	@Pattern(regexp="...?.?[-]....?[-]....?")
	private String phone;
	
	@Email
	@Column(length=50,nullable=false)
	private String email;
	
	@Column(length=100,nullable=false)
	private String address;
	
	@Range(min=1500,max=3000)
	@Column(length=5,nullable=false)
	private String graduation;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGraduation() {
		return graduation;
	}
	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}
}
