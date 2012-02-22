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

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import net.jforum.actions.helpers.Domain;
import net.jforum.core.SecurityConstraint;
import net.jforum.repository.BanlistDao;
import net.jforum.security.AdministrationRule;

/**
 * @author Rafael Steil
 */
@Resource
@Path(Domain.BANNING_ADMIN)
// @InterceptedBy(ActionSecurityInterceptor.class)
@SecurityConstraint(value = AdministrationRule.class, displayLogin = true)
public class BanlistAdminController {
	private BanlistDao repository;
	private final Result result;

	public BanlistAdminController(BanlistDao repository, Result result) {
		this.repository = repository;
		this.result = result;
	}

	public void list() {
		this.result.include("banlist", this.repository.getAllBanlists());
	}
}
