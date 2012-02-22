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

import net.jforum.entities.Category;
import net.jforum.entities.Forum;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Steil
 */
@Repository
public class CategoryDao extends HibernateGenericDAO<Category> implements Dao<Category> {
	public CategoryDao(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<Forum> getForums(Category category) {
		return session.createCriteria(Forum.class)
				.add(Restrictions.eq("category", category))
				.addOrder(Order.asc("displayOrder"))
				.setCacheable(true)
				.setCacheRegion("categoryDAO.getForums")
				.setComment("categoryDAO.getForums")
				.list();
	}

	@Override
	public void add(Category entity) {
		entity.setDisplayOrder(this.getMaxDisplayOrder());
		super.add(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Category> getAllCategories() {
		return session.createCriteria(this.persistClass)
				.addOrder(Order.asc("displayOrder"))
				.setCacheable(true)
				.setCacheRegion("categoryDAO.getAllCategories")
				.setComment("categoryDAO.getAllCategories")
				.list();
	}

	private int getMaxDisplayOrder() {
		Integer displayOrder = (Integer) session.createCriteria(this.persistClass)
				.setProjection(Projections.max("displayOrder"))
				.uniqueResult();

		return displayOrder == null ? 1 : displayOrder + 1;
	}
}
