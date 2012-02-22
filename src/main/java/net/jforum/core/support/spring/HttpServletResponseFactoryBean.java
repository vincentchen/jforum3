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
package net.jforum.core.support.spring;

import net.jforum.util.ConfigKeys;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Rafael Steil
 */
public class HttpServletResponseFactoryBean implements FactoryBean {
	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public Object getObject() throws Exception {
		RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
		return attributes.getAttribute(ConfigKeys.HTTP_SERVLET_RESPONSE, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class<?> getObjectType() {
		return HttpServletResponse.class;
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return false;
	}
}
