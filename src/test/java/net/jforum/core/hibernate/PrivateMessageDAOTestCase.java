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

import net.jforum.entities.PrivateMessage;
import net.jforum.entities.PrivateMessageType;
import net.jforum.entities.User;
import net.jforum.repository.PrivateMessageDao;
import net.jforum.repository.UserDao;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Steil
 */
public class PrivateMessageDAOTestCase extends AbstractDAOTestCase<PrivateMessage> {
	@Test
	public void deleteFromSentShouldNotRemoveOtherUserMessage() {
		User fromUser = this.createUser("fromUser");
		User toUser = this.createUser("toUser");

		PrivateMessageDao dao = this.newDao();
		PrivateMessage pm = this.newPm("subject", "text", fromUser, toUser);

		this.insert(pm, dao);

		List<PrivateMessage> sent = dao.getFromSentBox(fromUser);
		Assert.assertEquals(1, sent.size());

		this.delete(sent.get(0), dao);

		pm = dao.get(sent.get(0).getId());
		Assert.assertNull(pm);

		List<PrivateMessage> toUserInbox = dao.getFromInbox(toUser);
		Assert.assertEquals(1, toUserInbox.size());
	}

	@Test
	public void deleteFromInboxShouldNotRemoveOtherUserSentBox() {
		PrivateMessageDao dao = this.newDao();

		User fromUser = this.createUser("fromUser");
		User toUser = this.createUser("toUser");

		PrivateMessage pm = this.newPm("subject", "text", fromUser, toUser);
		this.insert(pm, dao);

		List<PrivateMessage> inbox = dao.getFromInbox(toUser);
		Assert.assertEquals(1, inbox.size());

		this.delete(inbox.get(0), dao);

		pm = dao.get(inbox.get(0).getId());
		Assert.assertNull(pm);

		List<PrivateMessage> fromUserSentBox = dao.getFromSentBox(fromUser);
		Assert.assertEquals(1, fromUserSentBox.size());
	}

	@Test
	public void insert() {
		PrivateMessageDao dao = this.newDao();

		User fromUser = this.createUser("fromUser");
		User toUser = this.createUser("toUser");

		PrivateMessage pm = this.newPm("subject", "text", fromUser, toUser);
		this.insert(pm, dao);

		Assert.assertTrue(pm.getId() > 0);

		PrivateMessage loaded = dao.get(pm.getId());
		Assert.assertNotNull(loaded);

		Assert.assertEquals("text", loaded.getText());
		Assert.assertEquals("subject", loaded.getSubject());
		Assert.assertEquals(fromUser, loaded.getFromUser());
		Assert.assertEquals(toUser, loaded.getToUser());
		Assert.assertEquals(PrivateMessageType.SENT, loaded.getType());

		// Check if the message is in the "Sent" folder
		List<PrivateMessage> sent = dao.getFromSentBox(fromUser);
		Assert.assertEquals(1, sent.size());
		Assert.assertEquals(loaded.getId(), sent.get(0).getId());

		// Check if the message is in the "Inbox" folder of the target user
		List<PrivateMessage> inbox = dao.getFromInbox(toUser);
		Assert.assertEquals(1, inbox.size());

		loaded = inbox.get(0);

		Assert.assertEquals("text", loaded.getText());
		Assert.assertEquals("subject", loaded.getSubject());
		Assert.assertEquals(fromUser, loaded.getFromUser());
		Assert.assertEquals(toUser, loaded.getToUser());
		Assert.assertEquals(PrivateMessageType.NEW, loaded.getType());
	}

	@Test
	public void selectFromInboxExpectZeroResults() {
		PrivateMessageDao dao = this.newDao();

		User user = new User(); user.setId(99);

		Assert.assertEquals(0, dao.getFromInbox(user).size());
	}

	@Test
	public void selectFromSentExpectZeroResults() {
		PrivateMessageDao dao = this.newDao();

		User user = new User(); user.setId(88);

		Assert.assertEquals(0, dao.getFromSentBox(user).size());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void updateExpectsException() {
		this.newDao().update(new PrivateMessage());
	}

	private PrivateMessage newPm(String subject, String text, User fromUser, User toUser) {
		PrivateMessage pm = new PrivateMessage();

		pm.setSubject(subject);
		pm.setText(text);

		pm.setFromUser(fromUser);
		pm.setToUser(toUser);

		return pm;
	}

	private PrivateMessageDao newDao() {
		return new PrivateMessageDao(session());
	}

	private User createUser(String username) {
		User user = new User();
		user.setUsername(username);

		UserDao dao = new UserDao(session());
		dao.add(user);

		return user;
	}
}
