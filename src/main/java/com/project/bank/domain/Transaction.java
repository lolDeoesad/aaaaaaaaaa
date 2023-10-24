package com.project.bank.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column(nullable = false, length = 20)
	private int money;
	
	@CreationTimestamp
	private Timestamp time;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depositor")
	private Account depositor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "withdrawal")
	private Account withdrawal;
	
	@Column(length = 100)
	private String memo;
	
}