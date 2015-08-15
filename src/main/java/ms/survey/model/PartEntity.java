package ms.survey.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="sns_part")
public class PartEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String partsn;
	private String value;
	private String img;
	private Boolean ans;
	private String ansText;
	private Integer rank;
	private ItemEntity item;
	
	@Id
	@Column(name="partsn")
	public String getPartsn() {
		return partsn;
	}
	public void setPartsn(String partsn) {
		this.partsn = partsn;
	}
	@Column(name="rank")
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	@Column(name="value")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Lob
	@Column(name="img")
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Column(name="ans")
	public Boolean getAns() {
		return ans;
	}
	public void setAns(Boolean ans) {
		this.ans = ans;
	}
	@Column(name="ans_text")
	public String getAnsText() {
		return ansText;
	}
	public void setAnsText(String ansText) {
		this.ansText = ansText;
	}
	
	@JsonBackReference
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="itemsn")
	public ItemEntity getItem() {
		return item;
	}
	public void setItem(ItemEntity item) {
		this.item = item;
	}
	@Override
	public String toString() {
		return "PartEntity [partsn=" + partsn + ", value=" + value + ", img=" + img + ", ans=" + ans + ", ansText="
				+ ansText + ", item=" + item.getItemsn() + "]";
	}
	
}
