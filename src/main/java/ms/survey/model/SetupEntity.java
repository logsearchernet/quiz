package ms.survey.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="sns_setup")
public class SetupEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String formid;
	private String title;
	private Boolean randomizedQuestion;
	private Boolean randomizedAnswer;
	private Boolean showAnswer;
	
	@Id
	@Column(name="formid")
	public String getFormid() {
		return formid;
	}
	public void setFormid(String formid) {
		this.formid = formid;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="randomized_question")
	public Boolean getRandomizedQuestion() {
		return randomizedQuestion;
	}
	public void setRandomizedQuestion(Boolean randomizedQuestion) {
		this.randomizedQuestion = randomizedQuestion;
	}
	@Column(name="randomized_answer")
	public Boolean getRandomizedAnswer() {
		return randomizedAnswer;
	}
	public void setRandomizedAnswer(Boolean randomizedAnswer) {
		this.randomizedAnswer = randomizedAnswer;
	}
	@Column(name="show_answer")
	public Boolean getShowAnswer() {
		return showAnswer;
	}
	public void setShowAnswer(Boolean showAnswer) {
		this.showAnswer = showAnswer;
	}
	@Override
	public String toString() {
		return "SetupEntity [formid=" + formid + ", title=" + title + ", randomizedQuestion=" + randomizedQuestion
				+ ", randomizedAnswer=" + randomizedAnswer + ", showAnswer=" + showAnswer + "]";
	}
	
	
}
