package com.tagtheagency.alcoholicsrecovered.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "forum_message")
public class ForumMessage {

	private int id;
	private ForumThread thread;
	private Date modifiedDate;
	private Date creationDate;
	private String subject;
	private User author;
	private String body;
//	private ForumMessage replyTo;
//	private List<ForumMessage> replies;


	@Id
	@GeneratedValue
	@Column(name = "message_id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thread_id", nullable = false)
	public ForumThread getThread() {
		return this.thread;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "reply_to_id", nullable = false)
//	public ForumMessage getReplyTo() {
//		return this.replyTo;
//	}
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="replyTo")
//	public List<ForumMessage> getReplies() {
//		return this.replies;
//	}

	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setThread(ForumThread thread) {
		this.thread = thread;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author", nullable = false)
	public User getAuthor() {
		return this.author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

//	public void setReplyTo(ForumMessage replyTo) {
//		this.replyTo = replyTo;
//	}
//	
//	public void setReplies(List<ForumMessage> replies) {
//		this.replies = replies;
//	}


}
