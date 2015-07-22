package org.online.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tabledir entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tabledir", catalog = "onlinefill")
public class Tabledir implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3328881355809127654L;
	private String dirid;
	private String ownername;
	private String text;
	private String pid;
	private Boolean leaf;

	// Constructors

	/** default constructor */
	public Tabledir() {
	}

	/** full constructor */
	public Tabledir(String dirid, String ownername, String text, String pid,
			Boolean leaf) {
		this.dirid = dirid;
		this.ownername = ownername;
		this.text = text;
		this.pid = pid;
		this.leaf = leaf;
	}

	// Property accessors
	@Id
	@Column(name = "dirid", unique = true, nullable = false, length = 32)
	public String getDirid() {
		return this.dirid;
	}

	public void setDirid(String dirid) {
		this.dirid = dirid;
	}

	@Column(name = "ownername", nullable = false, length = 50)
	public String getOwnername() {
		return this.ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	@Column(name = "text", nullable = false)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "pid", nullable = false, length = 32)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "leaf", nullable = false)
	public Boolean getLeaf() {
		return this.leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

}