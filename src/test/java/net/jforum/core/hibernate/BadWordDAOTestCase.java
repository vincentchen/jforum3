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
package net.jforum.core.hibernate;

import java.util.List;

import junit.framework.Assert;
import net.jforum.entities.BadWord;
import net.jforum.repository.BadWordDao;

import org.junit.Test;

/**
 * @author Rafael Steil
 */
public class BadWordDAOTestCase extends AbstractDAOTestCase<BadWord> {
	@Test
	public void getAll() {
		BadWord w1 = new BadWord(); w1.setWord("w1");
		BadWord w2 = new BadWord(); w2.setWord("w2");

		BadWordDao dao = this.newDao();

		this.insert(w1, dao);
		this.insert(w2, dao);

		List<BadWord> words = dao.getAll();
		Assert.assertEquals(2, words.size());
	}

	private BadWordDao newDao() {
		return new BadWordDao(session());
	}
}
