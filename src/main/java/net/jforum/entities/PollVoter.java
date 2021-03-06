/*
 * Copyright (c) JForum Team. All rights reserved.
 *
 * The software in this package is published under the terms of the LGPL
 * license a copy of which has been included with this distribution in the
 * license.txt file.
 *
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.entities;

import javax.persistence.*;

/**
 * @author Rafael Steil
 */
@Entity
@Table(name = "jforum_vote_voters")
public class PollVoter {
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "jforum_vote_voters_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
	@Column(name = "voter_id")
	public int id;

	@ManyToOne
	@JoinColumn(name = "vote_id")
	private Poll poll;

	@ManyToOne
	@JoinColumn(name = "vote_user_id")
	private User user;

	@Column(name = "vote_user_ip")
	private String ip;

	public Poll getPoll() {
		return this.poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
