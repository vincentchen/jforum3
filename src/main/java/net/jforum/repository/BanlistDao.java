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

import net.jforum.entities.Banlist;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Steil
 */
@Repository
public class BanlistDao extends HibernateGenericDAO<Banlist> implements Dao<Banlist> {
	public BanlistDao(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<Banlist> getAllBanlists() {
		return session.createCriteria(this.persistClass).list();
	}
}
