package org.online.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tablelist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tablelist", catalog = "onlinefill")
public class Tablelist implements java.io.Serializable {

	// Fields

	private String taid;
	private String tadirid;
	private String taname;
	private Timestamp tastdate;
	private Timestamp taendate;
	private Timestamp tapubdate;
	private Integer tafinicount;
	private String tafrom;
	private String tatype;

	// Constructors

	/** default constructor */
	public Tablelist() {
	}

	/** minimal constructor */
	public Tablelist(String taid, String tadirid, String taname,
			Integer tafinicount, String tafrom, String tatype) {
		this.taid = taid;
		this.tadirid = tadirid;
		this.taname = taname;
		this.tafinicount = tafinicount;
		this.tafrom = tafrom;
		this.tatype = tatype;
	}

	/** full constructor */
	public Tablelist(String taid, String tadirid, String taname,
			Timestamp tastdate, Timestamp taendate, Timestamp tapubdate,
			Integer tafinicount, String tafrom, String tatype) {
		this.taid = taid;
		this.tadirid = tadirid;
		this.taname = taname;
		this.tastdate = tastdate;
		this.taendate = taendate;
		this.tapubdate = tapubdate;
		this.tafinicount = tafinicount;
		this.tafrom = tafrom;
		this.tatype = tatype;
	}

	// Property accessors
	@Id
	@Column(name = "taid", unique = true, nullable = false, length = 32)
	public String getTaid() {
		return this.taid;
	}

	public void setTaid(String taid) {
		this.taid = taid;
	}

	@Column(name = "tadirid", nullable = false, length = 32)
	public String getTadirid() {
		return this.tadirid;
	}

	public void setTadirid(String tadirid) {
		this.tadirid = tadirid;
	}

	@Column(name = "taname", nullable = false, length = 1000)
	public String getTaname() {
		return this.taname;
	}

	public void setTaname(String taname) {
		this.taname = taname;
	}

	@Column(name = "tastdate", length = 19)
	public Timestamp getTastdate() {
		return this.tastdate;
	}

	public void setTastdate(Timestamp tastdate) {
		this.tastdate = tastdate;
	}

	@Column(name = "taendate", length = 19)
	public Timestamp getTaendate() {
		return this.taendate;
	}

	public void setTaendate(Timestamp taendate) {
		this.taendate = taendate;
	}

	@Column(name = "tapubdate", length = 19)
	public Timestamp getTapubdate() {
		return this.tapubdate;
	}

	public void setTapubdate(Timestamp tapubdate) {
		this.tapubdate = tapubdate;
	}

	@Column(name = "tafinicount", nullable = false)
	public Integer getTafinicount() {
		return this.tafinicount;
	}

	public void setTafinicount(Integer tafinicount) {
		this.tafinicount = tafinicount;
	}

	@Column(name = "tafrom", nullable = false, length = 32)
	public String getTafrom() {
		return this.tafrom;
	}

	public void setTafrom(String tafrom) {
		this.tafrom = tafrom;
	}

	@Column(name = "tatype", nullable = false, length = 45)
	public String getTatype() {
		return this.tatype;
	}

	public void setTatype(String tatype) {
		this.tatype = tatype;
	}

}