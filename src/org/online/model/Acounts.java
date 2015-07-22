package org.online.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Acounts entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "acounts", catalog = "onlinefill")
public class Acounts implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7542538629304934036L;
	private String acname;
	private String acorgid;
	private String acpwd;
	private String acowner;
	private String acemail;
	private String acphone;
	private Timestamp accdate;
	private Boolean acstatu;
	private String acdetail;

	// Constructors

	/** default constructor */
	public Acounts() {
	}

	/** minimal constructor */
	public Acounts(String acname, String acorgid, String acpwd, String acowner,
			Boolean acstatu) {
		this.acname = acname;
		this.acorgid = acorgid;
		this.acpwd = acpwd;
		this.acowner = acowner;
		this.acstatu = acstatu;
	}

	/** full constructor */
	public Acounts(String acname, String acorgid, String acpwd, String acowner,
			String acemail, String acphone, Timestamp accdate, Boolean acstatu,
			String acdetail) {
		this.acname = acname;
		this.acorgid = acorgid;
		this.acpwd = acpwd;
		this.acowner = acowner;
		this.acemail = acemail;
		this.acphone = acphone;
		this.accdate = accdate;
		this.acstatu = acstatu;
		this.acdetail = acdetail;
	}

	// Property accessors
	@Id
	@Column(name = "acname", unique = true, nullable = false, length = 50)
	public String getAcname() {
		return this.acname;
	}

	public void setAcname(String acname) {
		this.acname = acname;
	}

	@Column(name = "acorgid", nullable = false, length = 32)
	public String getAcorgid() {
		return this.acorgid;
	}

	public void setAcorgid(String acorgid) {
		this.acorgid = acorgid;
	}

	@Column(name = "acpwd", nullable = false, length = 50)
	public String getAcpwd() {
		return this.acpwd;
	}

	public void setAcpwd(String acpwd) {
		this.acpwd = acpwd;
	}

	@Column(name = "acowner", nullable = false, length = 50)
	public String getAcowner() {
		return this.acowner;
	}

	public void setAcowner(String acowner) {
		this.acowner = acowner;
	}

	@Column(name = "acemail", length = 50)
	public String getAcemail() {
		return this.acemail;
	}

	public void setAcemail(String acemail) {
		this.acemail = acemail;
	}

	@Column(name = "acphone", length = 50)
	public String getAcphone() {
		return this.acphone;
	}

	public void setAcphone(String acphone) {
		this.acphone = acphone;
	}

	@Column(name = "accdate", length = 19)
	public Timestamp getAccdate() {
		return this.accdate;
	}

	public void setAccdate(Timestamp accdate) {
		this.accdate = accdate;
	}

	@Column(name = "acstatu", nullable = false)
	public Boolean getAcstatu() {
		return this.acstatu;
	}

	public void setAcstatu(Boolean acstatu) {
		this.acstatu = acstatu;
	}

	@Column(name = "acdetail", length = 4000)
	public String getAcdetail() {
		return this.acdetail;
	}

	public void setAcdetail(String acdetail) {
		this.acdetail = acdetail;
	}

}