package com.kenta.tabuchi;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;


public class Student {

	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String namePhonetic;
	
	@Pattern(regexp="....[/]..[/]..")
	private String birthday;
	
	@Pattern(regexp="...?.?[-]....?[-]....?")
	private String phone;
	
	@Email
	private String email;
	
	private String address;
	
	@Range(min=1500,max=3000)
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
	public String getNamePhonetic() {
		return namePhonetic;
	}
	public void setNamePhonetic(String namePhonetic) {
		this.namePhonetic = namePhonetic;
	}
}
