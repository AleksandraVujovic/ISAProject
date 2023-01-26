package com.example.BloodBank.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@Entity()
public class News implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String text;
	private Boolean isSent;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "bank_id", referencedColumnName = "bankID")
	private BloodBank bloodBank;

	public News() {
	}
	public News(Long id, String title, String text,
				Boolean isSent, BloodBank bloodBank) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.isSent = isSent;
		this.bloodBank = bloodBank;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getSent() {
		return isSent;
	}

	public void setSent(Boolean sent) {
		isSent = sent;
	}

	public BloodBank getBloodBank() {
		return bloodBank;
	}

	public void setBloodBank(BloodBank bloodBank) {
		this.bloodBank = bloodBank;
	}
}
