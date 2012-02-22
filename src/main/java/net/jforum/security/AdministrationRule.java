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
import net.jforum.core.SecurityConstraint;
import net.jforum.entities.UserSession;

import javax.servlet.http.HttpServletRequest;

/**
 * Check if the user can access the Admin Panel.
 * This is intended to be used with {@link SecurityConstraint}, and will check
 * if the current user can access the Administration Panel
 *
 * @author Rafael Steil
 */
@Component
public class AdministrationRule implements AccessRule {
	/**
	 * Applies the following rules:
	 * <ul>
	 * <li> Must must be logged
	 * <li> Should be an Administrator
	 * </ul>
	 */
	@Override
	public boolean shouldProceed(UserSession userSession, HttpServletRequest request) {
		return userSession.isLogged()
				&& (userSession.getRoleManager().isAdministrator() || userSession.getRoleManager().isCoAdministrator());
	}
}
