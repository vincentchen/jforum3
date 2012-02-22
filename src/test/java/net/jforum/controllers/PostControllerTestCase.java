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

import net.jforum.actions.helpers.Actions;
import net.jforum.actions.helpers.AttachedFile;
import net.jforum.actions.helpers.Domain;
import net.jforum.actions.helpers.PostFormOptions;
import net.jforum.entities.Forum;
import net.jforum.entities.ModerationLog;
import net.jforum.entities.PollOption;
import net.jforum.entities.Post;
import net.jforum.entities.Smilie;
import net.jforum.entities.Topic;
import net.jforum.entities.UserSession;
import net.jforum.repository.PostDao;
import net.jforum.repository.SmilieDao;
import net.jforum.repository.TopicDao;
import net.jforum.services.PostService;
import net.jforum.util.ConfigKeys;
import net.jforum.util.JForumConfig;
import net.jforum.util.TestCaseUtils;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

/**
 * @author Rafael Steil
 */
public class PostControllerTestCase {
	private Mockery context = TestCaseUtils.newMockery();
	private PostDao postRepository = context.mock(PostDao.class);
	private SmilieDao smilieRepository = context.mock(SmilieDao.class);
	private TopicDao topicRepository = context.mock(TopicDao.class);
	private PostService postService = context.mock(PostService.class);
	private JForumConfig config = context.mock(JForumConfig.class);
	private UserSession userSession = context.mock(UserSession.class);
	private Result mockResult = context.mock(MockResult.class);
	private TopicController mockTopicController = context.mock(TopicController.class);
	private ForumController mockForumController = context.mock(ForumController.class);

	private PostController controller = new PostController(postRepository,
		smilieRepository, postService, config, userSession, null, null, mockResult);
	private ModerationLog moderationLog = new ModerationLog();

	@Test
	public void deleteHasMorePostsShouldRedirectToTopicListing() {
		this.deleteRedirect(1, 0);
	}

	@Test
	public void deleteHasMorePostsShouldRedirectToPage3() {
		this.deleteRedirect(14, 3);
	}

	private void deleteRedirect(final int totalPosts, final int expectedPage) {
		final Post post = new Post();
		post.setId(2);
		post.setTopic(new Topic() {
			@Override
			public int getTotalPosts() {
				return totalPosts;
			}
		});
		post.getTopic().setId(7);

		context.checking(new Expectations() {
			{
				one(postRepository).get(2); will(returnValue(post));
				one(postService).delete(post);
			}
		});

		this.redirectToPage(post.getTopic(), expectedPage);

		controller.delete(2);
		context.assertIsSatisfied();
	}

	@Test
	public void deleteLastMessageShouldRedirectToForum() {
		final Post post = new Post();
		post.setId(2);
		post.setTopic(new Topic(topicRepository));
		post.getTopic().getForum().setId(3);

		context.checking(new Expectations() {
			{
				one(postRepository).get(2);
				will(returnValue(post));
				one(postService).delete(post);
				post.getTopic().decrementTotalReplies(); // we simulate the
															// event dispatch

				// TODO pass zero?
				one(mockResult).redirectTo(ForumController.class);
				will(returnValue(mockForumController));
				one(mockForumController).show(
						post.getTopic().getForum().getId(), 0);
			}
		});

		controller.delete(2);
		context.assertIsSatisfied();
	}

	@Test
	public void editSave() {
		final Post post = new Post();
		post.setTopic(new Topic());
		post.setForum(new Forum());
		final PostFormOptions options = new PostFormOptions();

		context.checking(new Expectations() {
			{
				ignoring(userSession);
				one(postRepository).get(0);
				will(returnValue(post));
				one(postService).update(post, false,
						new ArrayList<PollOption>(),
						new ArrayList<AttachedFile>(), moderationLog);

				// TODO pass zero and true?
				one(mockResult).redirectTo(TopicController.class);
				will(returnValue(mockTopicController));
				one(mockTopicController).list(post.getTopic().getId(), 0, true);

			}
		});

		controller.editSave(post, options, null, moderationLog);
		context.assertIsSatisfied();
	}

	@Test
	public void edit() {
		final Post post = new Post() {
			private static final long serialVersionUID = 1L;

			@Override
			public Topic getTopic() {
				return new Topic() {
					private static final long serialVersionUID = 1L;

					@Override
					public Forum getForum() {
						return new Forum();
					}
				};
			}
		};

		context.checking(new Expectations() {
			{
				one(postRepository).get(1);
				will(returnValue(post));
				one(smilieRepository).getAllSmilies();
				will(returnValue(new ArrayList<Smilie>()));
				one(mockResult).include("post", post);
				one(mockResult).include("isEdit", true);
				one(mockResult).include("topic", new Topic());
				one(mockResult).include("forum", new Forum());
				one(mockResult).include("smilies", new ArrayList<Smilie>());

				// TODO pass zero?
				one(mockResult).forwardTo(TopicController.class);
				will(returnValue(mockTopicController));
				one(mockTopicController).add(0);
			}
		});

		controller.edit(1);
		context.assertIsSatisfied();
	}

	private void redirectToPage(final Topic topic, final int expectedPage) {
		context.checking(new Expectations() {
			{
				one(config).getInt(ConfigKeys.POSTS_PER_PAGE);
				will(returnValue(5));

				String url;

				if (expectedPage > 0) {
					url = String.format("/%s/%s/%s/%s.page", Domain.TOPICS, Actions.LIST, expectedPage, topic.getId());
				}
				else {
					url = String.format("/%s/%s/%s.page", Domain.TOPICS, Actions.LIST, topic.getId());
				}

				one(mockResult).redirectTo(url);
			}
		});
	}
}
