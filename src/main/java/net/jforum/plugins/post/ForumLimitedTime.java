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
package net.jforum.plugins.post;

import net.jforum.entities.Forum;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Bill
 */
@Entity
@Table(name = "jforum_forums_limited_time")
public class ForumLimitedTime implements Serializable {
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "jforum_limited_time_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
	@Column(name = "id")
	private int id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forum_id", updatable = false, unique = true)
	private Forum forum;

	@Column(name = "limited_time")
	private long limitedTime;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the forum
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forum the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	/**
	 * @return the limitedTime
	 */
	public long getLimitedTime() {
		return limitedTime;
	}

	/**
	 * @param limitedTime the limitedTime to set
	 */
	public void setLimitedTime(long limitedTime) {
		this.limitedTime = limitedTime;
	}
}
