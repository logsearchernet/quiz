package ms.survey.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name="count",
                query="select count(c) from FormEntity c where c.email=:email")
})
@Table(name="sns_form")
public class FormEntity implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String formid;
	private SetupEntity setup;
	private String email;
	private List<PageEntity> pages;
	private Date dateAdded;
	private Date dateModified;

	@Id
	@Column(name="formid")
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER) 
	public SetupEntity getSetup() { 
		return setup;
	}

	public void setSetup(SetupEntity setup) {
		this.setup = setup;
	}

	@OrderBy("rank ASC")
	@Fetch(FetchMode.SELECT)
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="form")
	public List<PageEntity> getPages() {
		return pages;
	}

	public void setPages(List<PageEntity> pages) {
		this.pages = pages;
	}

	@Column(name="date_added")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Column(name="date_modified")
	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	public String toString() {
		return "FormEntity [formid=" + formid + ", setup=" + setup + ", email=" + email + ", pages.size=" + pages.size()
				+ ", dateAdded=" + dateAdded + ", dateModified=" + dateModified + "]";
	}

	
}
