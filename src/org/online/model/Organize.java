package org.online.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Organize entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "organize", catalog = "onlinefill")
public class Organize implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2819086231992884632L;
	private String orgid;
	private String text;
	private String pid;
	private Boolean leaf;

	// Constructors

	/** default constructor */
	public Organize() {
	}

	/** full constructor */
	public Organize(String orgid, String text, String pid, Boolean leaf) {
		this.orgid = orgid;
		this.text = text;
		this.pid = pid;
		this.leaf = leaf;
	}

	// Property accessors
	@Id
	@Column(name = "orgid", unique = true, nullable = false, length = 32)
	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
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