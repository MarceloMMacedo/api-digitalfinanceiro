package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Events implements Serializable {

	private static final long serialVersionUID = 1L;

	private String start;
	private String end;
	private String date; 
	private String id; 
	private String title; 
	private String color;

	public Events(String start, String id, String title, String color) {
		super();
		this.start = start;
		this.id = id;
		this.title = title;
		this.color = color;
	}
}
