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

import net.jforum.entities.Config;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Steil
 * @author Jose Donizetti de Brito Junior
 */
@Repository
public class ConfigDao extends HibernateGenericDAO<Config> implements Dao<Config> {

	public ConfigDao(Session session) {
		super(session);
	}

	@Override
	public void update(Config entity) {
		session.saveOrUpdate(entity);
	}

	public Config getByName(String configName) {
		return (Config) session.createCriteria(this.persistClass)
				.add(Restrictions.eq("name", configName))
				.setCacheable(true)
				.setCacheRegion("configDAO")
				.setComment("configDAO.getByName")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Config> getAll() {
		return session.createCriteria(this.persistClass).list();
	}
}
