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
package net.jforum.core.events.forum;

import net.jforum.entities.Forum;
import net.jforum.entities.Group;
import net.jforum.entities.UserSession;
import net.jforum.events.EmptyForumEvent;
import net.jforum.repository.GroupDao;
import net.jforum.services.GroupService;
import net.jforum.util.SecurityConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Steil
 */
public class NewForumGroupPermissionsEvent extends EmptyForumEvent {
	private GroupDao groupRepository;
	private GroupService groupService;
	private final UserSession userSession;

	public NewForumGroupPermissionsEvent(GroupDao groupRepository, GroupService groupService, UserSession userSession) {
		this.groupRepository = groupRepository;
		this.groupService = groupService;
		this.userSession = userSession;
	}

	/**
	 * When a new forum is added, set group access automatically.
	 * Every group which is an Administrator and every user group which is
	 * Co Administrator will have access by default to the new forum.
	 *
	 * @see net.jforum.events.EmptyForumEvent#added(net.jforum.entities.Forum)
	 */
	@Override
	public void added(Forum forum) {
		List<Group> allGroups = this.groupRepository.getAllGroups();
		List<Group> userGroups = this.userSession.getUser().getGroups();
		List<Group> processedGroups = new ArrayList<Group>();

		for (Group group : userGroups) {
			if (this.isGoodCandidate(group)) {
				processedGroups.add(group);
				this.groupService.appendRole(group, SecurityConstants.FORUM, forum.getId());
			}
		}

		for (Group group : allGroups) {
			if (!processedGroups.contains(group) && group.roleExist(SecurityConstants.ADMINISTRATOR)) {
				this.groupService.appendRole(group, SecurityConstants.FORUM, forum.getId());
			}
		}
	}

	private boolean isGoodCandidate(Group group) {
		return group.roleExist(SecurityConstants.ADMINISTRATOR)
				|| group.roleExist(SecurityConstants.CO_ADMINISTRATOR);
	}
}
