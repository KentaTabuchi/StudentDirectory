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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name="M_Student")
@JsonPropertyOrder({"ID", "名前", "概要", "更新日時"})
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@JsonProperty("ID")
	private Long id;
	
	@Column(length=50,nullable=false)
	@NotEmpty
	@JsonProperty("名前")
	private String name;
	
	@Column(length=50,nullable=false)
	@NotEmpty
	@JsonProperty("ふりがな")
	private String namePhonetic;
	
	@Column(nullable=true)
	@Pattern(regexp="....[/]..[/]..")
	@JsonProperty("誕生日")
	private String birthday;
	
	@Column(length=18,nullable=true)
	@Pattern(regexp="...?.?[-]....?[-]....?")
	@JsonProperty("電話番号")
	private String phone;
	
	@Email
	@Column(length=50,nullable=false)
	@JsonProperty("E-mail")
	private String email;
	
	@Column(length=100,nullable=false)
	@JsonProperty("住所")
	private String address;
	
	@Range(min=1500,max=3000)
	@Column(length=5,nullable=false)
	@JsonProperty("卒業年")
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
