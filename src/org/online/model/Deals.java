package org.online.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Deals entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "deals", catalog = "onlinefill")
public class Deals implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5850320915618070180L;
	private String deid;
	private String tadeid;
	private Boolean destatu;
	private String acdeid;

	// Constructors

	/** default constructor */
	public Deals() {
	}

	/** full constructor */
	public Deals(String deid, String tadeid, Boolean destatu, String acdeid) {
		this.deid = deid;
		this.tadeid = tadeid;
		this.destatu = destatu;
		this.acdeid = acdeid;
	}

	// Property accessors
	@Id
	@Column(name = "deid", unique = true, nullable = false, length = 32)
	public String getDeid() {
		return this.deid;
	}

	public void setDeid(String deid) {
		this.deid = deid;
	}

	@Column(name = "tadeid", nullable = false, length = 32)
	public String getTadeid() {
		return this.tadeid;
	}

	public void setTadeid(String tadeid) {
		this.tadeid = tadeid;
	}

	@Column(name = "destatu", nullable = false)
	public Boolean getDestatu() {
		return this.destatu;
	}

	public void setDestatu(Boolean destatu) {
		this.destatu = destatu;
	}

	@Column(name = "acdeid", nullable = false, length = 32)
	public String getAcdeid() {
		return this.acdeid;
	}

	public void setAcdeid(String acdeid) {
		this.acdeid = acdeid;
	}

}