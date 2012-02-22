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

import br.com.caelum.vraptor.ioc.Component;
import net.jforum.entities.Forum;
import net.jforum.repository.Dao;
import net.jforum.repository.HibernateGenericDAO;
import org.hibernate.Session;

/**
 * @author Bill
 */
@Component
public class ForumLimitedTimeDao extends HibernateGenericDAO<ForumLimitedTime> implements Dao<ForumLimitedTime> {

	public ForumLimitedTimeDao(Session session) {
		super(session);
	}

	/**
	 * @see ForumLimitedTimeDao#getLimitedTime(net.jforum.entities.Forum)
	 */
	public long getLimitedTime(Forum forum) {
		ForumLimitedTime forumLimited = this.getForumLimitedTime(forum);
		return forumLimited != null ? forumLimited.getLimitedTime() : 0;
	}

	public ForumLimitedTime getForumLimitedTime(Forum forum) {
		return (ForumLimitedTime) session.createQuery("from ForumLimitedTime f where f.forum = :forum")
				.setParameter("forum", forum)
				.setMaxResults(1).uniqueResult();
	}

	/**
	 * @see ForumLimitedTimeDao#saveOrUpdate(net.jforum.plugins.post.ForumLimitedTime)
	 */
	public void saveOrUpdate(ForumLimitedTime fourmLimitedTime) {
		session.saveOrUpdate(fourmLimitedTime);
	}
}
