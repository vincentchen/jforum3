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

import java.util.ArrayList;

import net.jforum.entities.Smilie;
import net.jforum.repository.SmilieDao;
import net.jforum.services.SmilieService;
import net.jforum.util.TestCaseUtils;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.util.test.MockResult;

/**
 * @author Rafael Steil
 */
public class SmilieAdminControllerTestCase extends AdminTestCase {
	private Mockery context = TestCaseUtils.newMockery();
	private SmilieDao repository = context.mock(SmilieDao.class);
	private SmilieService service = context.mock(SmilieService.class);
	private Result mockResult = context.mock(MockResult.class);
	private SmilieAdminController controller = new SmilieAdminController(service,
			repository, mockResult);
	private SmilieAdminController mockSmilieAdminController = context.mock(SmilieAdminController.class);

	public SmilieAdminControllerTestCase() {
		super(SmilieAdminController.class);
	}

	@Test
	public void edit() {
		context.checking(new Expectations() {
			{
				one(repository).get(1);
				will(returnValue(new Smilie()));
				one(mockResult).include("smilie", new Smilie());
				one(mockResult).forwardTo(controller);
				will(returnValue(mockSmilieAdminController));
				one(mockSmilieAdminController).add();
			}
		});

		controller.edit(1);
		context.assertIsSatisfied();
	}

	@Test
	public void editSave() {
		context.checking(new Expectations() {
			{
				one(service).update(with(aNonNull(Smilie.class)),
						with(aNull(UploadedFile.class)));
				one(mockResult).redirectTo(controller);
				will(returnValue(mockSmilieAdminController));
				one(mockSmilieAdminController).list();
			}
		});

		controller.editSave(new Smilie(), null);
		context.assertIsSatisfied();
	}

	@Test
	public void delete() {
		context.checking(new Expectations() {
			{
				one(service).delete(1, 2, 3);
				one(mockResult).redirectTo(controller);
				will(returnValue(mockSmilieAdminController ));
				one(mockSmilieAdminController).list();
			}
		});

		controller.delete(1, 2, 3);
		context.assertIsSatisfied();
	}

	@Test
	public void listExpectOneRecord() {
		context.checking(new Expectations() {
			{
				one(repository).getAllSmilies();
				will(returnValue(new ArrayList<Smilie>()));
				one(mockResult).include("smilies", new ArrayList<Smilie>());
			}
		});

		controller.list();
		context.assertIsSatisfied();
	}

	@Test
	public void addSave() {
		context.checking(new Expectations() {
			{
				one(service).add(with(aNonNull(Smilie.class)),
						with(aNull(UploadedFile.class)));
				one(mockResult).redirectTo(controller);
				will(returnValue(mockSmilieAdminController));
				one(mockSmilieAdminController).list();
			}
		});

		controller.addSave(new Smilie(), null);
		context.assertIsSatisfied();
	}
}
