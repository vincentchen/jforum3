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
package net.jforum.entities;

import javax.persistence.*;

/**
 * @author Rafael Steil
 */
@Entity
@Table(name = "jforum_themes")
public class Theme {
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "jforum_themes_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
	@Column(name = "theme_id")
	private int id;

	@Column(name = "style_name")
	private String styleName;

	@Column(name = "template_name")
	private String templateName;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStyleName() {
		return this.styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
