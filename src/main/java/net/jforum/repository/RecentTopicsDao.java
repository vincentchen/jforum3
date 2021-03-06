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
package net.jforum.repository;

import net.jforum.entities.Topic;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Steil
 */
@Repository
public class RecentTopicsDao extends HibernateGenericDAO<Topic> implements Dao<Topic> {
	public RecentTopicsDao(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<Topic> getNewTopics(int count) {
		return session.createCriteria(this.persistClass)
				.add(Restrictions.eq("pendingModeration", false))
				.addOrder(Order.desc("id"))
				.setMaxResults(count)
				.setCacheable(true)
				.setCacheRegion("recentTopicsDAO")
				.setComment("recentTopicsDAO.getRecentTopics")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Topic> getUpdatedTopics(int count) {
		return session.createCriteria(this.persistClass)
				.add(Restrictions.eq("pendingModeration", false))
				.createAlias("lastPost", "lastPost")
				.addOrder(Order.desc("lastPost.id"))
				.setMaxResults(count)
				.setCacheable(true)
				.setCacheRegion("recentTopicsDAO")
				.setComment("recentTopicsDAO.getRecentTopics")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Topic> getHotTopics(int count) {
		return session.createCriteria(this.persistClass)
				.add(Restrictions.eq("pendingModeration", false))
				.createAlias("lastPost", "lastPost")
				.addOrder(Order.desc("totalReplies"))
				.setMaxResults(count)
				.setCacheable(true)
				.setCacheRegion("recentTopicsDAO")
				.setComment("recentTopicsDAO.getRecentTopics")
				.list();
	}
}
