package ms.survey.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="sns_item")
public class ItemEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemsn;
	private String type;
	private String question;
	private String img;
	private Integer col;
	private Integer row;
	private Integer rank;
	private List<PartEntity> parts;
	private PageEntity page;
	
	@Id
	@Column(name="itemsn")
	public String getItemsn() {
		return itemsn;
	}
	public void setItemsn(String itemsn) {
		this.itemsn = itemsn;
	}
	@Column(name="rank")
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="question")
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	@Lob
	@Column(name="img")
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Column(name="col")
	public Integer getCol() {
		return col;
	}
	public void setCol(Integer col) {
		this.col = col;
	}
	@Column(name="row")
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	
	@OrderBy("rank ASC")
	@Fetch (FetchMode.SELECT)
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="item")
	public List<PartEntity> getParts() {
		return parts;
	}
	public void setParts(List<PartEntity> parts) {
		this.parts = parts;
	}
	
	@JsonBackReference
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="pagesn")
	public PageEntity getPage() {
		return page;
	}
	public void setPage(PageEntity page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "ItemEntity [itemsn=" + itemsn + ", type=" + type + ", question=" + question + ", img=" + img + ", col="
				+ col + ", row=" + row + ", parts=" + parts.size() + ", page=" + page.getPagesn() + "]";
	}
	
	
}
