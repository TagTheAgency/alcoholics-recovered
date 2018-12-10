package com.tagtheagency.alcoholicsrecovered.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "forum_thread")
public class ForumThread {
	
	private int id;
	private List<ForumMessage> forumMessages;
//	private ForumMessage rootMessage;
	private Date startDate;
	private String subject;
	
//	private Map<Integer, ForumMessage> messageMap;


	@Id
	@GeneratedValue
	@Column(name = "thread_id", unique = true, nullable = false)
	public int getThreadId() {
		return this.id;
	}

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "thread")
	public List<ForumMessage> getForumMessages() {
		return this.forumMessages;
	}
	
//	@OneToOne
//	@JoinColumn(name = "root_message_id")
//	public ForumMessage getRootMessage() {
//		return this.rootMessage;
//	}
	
	@Column(name = "thread_start_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return this.startDate;
	}
	
	@Transient
	public int getMessageCount() {
		return getForumMessages().size();
	}

//	@Transient
//	public ForumMessage getMessage(long messageId) {
//		populateMap();
//		return messageMap.get((int) messageId);
//	}
//
//	@Transient
//	private void populateMap() {
//		if (messageMap != null && messageMap.size() == forumMessages.size()) {
//			return;
//		}
//		messageMap = new HashMap<Integer, ForumMessage>();
//		for (ForumMessage message : forumMessages) {
//			messageMap.put(message.getId(), message);
//		}
//		
//		
//	}

	public void setThreadId(int id) {
		this.id = id;
	}

//	public void setRootMessage(ForumMessage rootMessage) {
//		this.rootMessage = rootMessage;
//	}

	public void setForumMessages(List<ForumMessage> forumMessages) {
		this.forumMessages = forumMessages;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
