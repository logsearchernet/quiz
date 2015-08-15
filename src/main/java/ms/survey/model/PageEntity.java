package ms.survey.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="sns_page")
public class PageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pagesn;
	private Integer rank;
	private List<ItemEntity> items;
	private FormEntity form;
	
	@Id
	@Column(name="pagesn")
	public String getPagesn() {
		return pagesn;
	}
	public void setPagesn(String pagesn) {
		this.pagesn = pagesn;
	}
	
	@Column(name="rank")
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	@OrderBy("rank ASC")
	@Fetch (FetchMode.SELECT)
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="page")
	public List<ItemEntity> getItems() {
		return items;
	}
	public void setItems(List<ItemEntity> items) {
		this.items = items;
	}
	
	@JsonBackReference
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="formid")
	public FormEntity getForm() {
		return form;
	}
	public void setForm(FormEntity form) {
		this.form = form;
	}
	@Override
	public String toString() {
		return "PageEntity [pagesn=" + pagesn + ", items.size=" + items.size() + ", formid=" + form.getFormid() + "]";
	}
	
	
	
	
}
