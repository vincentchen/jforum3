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
package net.jforum.controllers;

import net.jforum.actions.helpers.Domain;
import net.jforum.core.SecurityConstraint;
import net.jforum.core.SessionManager;
import net.jforum.repository.ForumDao;
import net.jforum.security.AdministrationRule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 * @author Rafael Steil
 */
@Controller()
@RequestMapping(value = Domain.ROOT + Domain.ADMIN)
@SecurityConstraint(value = AdministrationRule.class, displayLogin = true)
public class AdminController {
	@Inject
	private SessionManager sessionManager;
	@Inject
	private ForumDao forumRepository;

	/**
	 * Shows the main administration page (for logged users)
	 */
	public void index() {
	}

	/**
	 * The left navigation menu
	 */
	public void menu() {

	}

	/**
	 * The main admin page
	 */
	public void main(Model result) {
		result.addAttribute("stats", this.forumRepository.getForumStats());
		result.addAttribute("sessions", this.sessionManager.getLoggedSessions());
		result.addAttribute("totalLoggedUsers", this.sessionManager.getTotalLoggedUsers());
	}
}
