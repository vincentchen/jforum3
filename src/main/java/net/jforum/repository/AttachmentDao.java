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

import net.jforum.entities.Attachment;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author Rafael Steil
 */
@Repository
public class AttachmentDao extends HibernateGenericDAO<Attachment> implements Dao<Attachment> {
	public AttachmentDao(Session session) {
		super(session);
	}
}
