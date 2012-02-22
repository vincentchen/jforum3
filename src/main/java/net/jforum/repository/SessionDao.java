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

import net.jforum.entities.Session;
import org.springframework.stereotype.Repository;

/**
 * @author Rafael Steil
 */
@Repository
public class SessionDao extends HibernateGenericDAO<Session> implements Dao<Session> {
	public SessionDao(org.hibernate.Session session) {
		super(session);
	}

	/**
	 * Always execute saveOrUpdate().
	 */
	@Override
	public void add(Session entity) {
		session.saveOrUpdate(entity);
	}

	/**
	 * Always execute saveOrUpdate()
	 */
	@Override
	public void update(Session entity) {
		session.saveOrUpdate(entity);
	}
}
