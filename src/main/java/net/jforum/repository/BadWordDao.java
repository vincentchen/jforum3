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

import net.jforum.entities.BadWord;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Steil
 */
@Repository
public class BadWordDao extends HibernateGenericDAO<BadWord> implements Dao<BadWord> {

	public BadWordDao(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<BadWord> getAll() {
		return session.createCriteria(BadWord.class).list();
	}
}
