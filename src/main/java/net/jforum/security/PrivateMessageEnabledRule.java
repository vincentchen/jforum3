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
package net.jforum.security;

import br.com.caelum.vraptor.ioc.Component;
import net.jforum.entities.UserSession;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Steil
 */
@Component
public class PrivateMessageEnabledRule implements AccessRule {

	/**
	 * @see net.jforum.security.AccessRule#shouldProceed(net.jforum.entities.UserSession, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean shouldProceed(UserSession userSession, HttpServletRequest request) {
		return userSession.getRoleManager().isPrivateMessageEnabled();
	}

}
