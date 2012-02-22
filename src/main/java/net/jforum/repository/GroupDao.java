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

import net.jforum.entities.Group;
import net.jforum.entities.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Steil
 */
@Repository
public class GroupDao extends HibernateGenericDAO<Group> implements Dao<Group> {
	public GroupDao(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<Group> getAllGroups() {
		return session.createCriteria(this.persistClass).list();
	}

	public Group getByName(String groupName) {
		return (Group) session.createCriteria(this.persistClass)
				.add(Restrictions.eq("name", groupName))
				.uniqueResult();
	}

	@Override
	public void remove(Group group) {
		List<User> users = group.getUsers();
		for (User user : users) {
			List<Group> groups = user.getGroups();
			groups.remove(group);
			session.save(user);
		}
		super.remove(group);
	}


}
