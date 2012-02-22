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
package net.jforum.services;

import net.jforum.entities.ModerationLog;
import net.jforum.entities.Post;
import net.jforum.entities.Topic;
import net.jforum.entities.User;
import net.jforum.repository.ModerationLogDao;
import net.jforum.repository.TopicDao;
import net.jforum.util.ConfigKeys;
import net.jforum.util.JForumConfig;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Rafael Steil
 */
@Service
public class ModerationLogService {
	private final JForumConfig config;
	private final ModerationLogDao repository;
	private final TopicDao topicRepository;

	public ModerationLogService(JForumConfig config, ModerationLogDao repository, TopicDao topicRepository) {
		this.config = config;
		this.repository = repository;
		this.topicRepository = topicRepository;
	}

	public void registerPostEdit(ModerationLog log, Post post, String originalPostMessage) {
		if (this.isLoggingEnabled(log)) {
			log.setDate(new Date());
			log.setOriginalMessage(originalPostMessage);
			log.setPosterUser(post.getUser());
			log.setPostId(post.getId());
			log.setTopicId(post.getTopic().getId());

			this.repository.add(log);
		}
	}

	public void registerMovedTopics(ModerationLog log, int... topicIds) {
		if (this.isLoggingEnabled(log)) {
			for (int topicId : topicIds) {
				ModerationLog ml = this.createModerationLog(log.getType(), log.getUser(), log.getDescription(),
						this.topicRepository.get(topicId).getUser());

				ml.setTopicId(topicId);

				this.repository.add(ml);
			}
		}
	}

	public void registerLockedTopics(ModerationLog log, int[] topicIds) {
		if (this.isLoggingEnabled(log)) {
			for (int topicId : topicIds) {
				ModerationLog ml = this.createModerationLog(log.getType(), log.getUser(), log.getDescription(),
						this.topicRepository.get(topicId).getUser());

				ml.setTopicId(topicId);

				this.repository.add(ml);
			}
		}
	}

	public void registerDeleteTopics(List<Topic> topics, ModerationLog log) {
		if (this.isLoggingEnabled(log)) {
			for (Topic topic : topics) {
				ModerationLog ml = this.createModerationLog(log.getType(), log.getUser(), log.getDescription(), topic.getUser());
				this.repository.add(ml);
			}
		}
	}

	private ModerationLog createModerationLog(int type, User user, String description, User posterUser) {
		ModerationLog ml = new ModerationLog();

		ml.setDate(new Date());
		ml.setType(type);
		ml.setUser(user);
		ml.setDescription(description);
		ml.setPosterUser(posterUser);

		return ml;
	}

	private boolean isLoggingEnabled(ModerationLog log) {
		return log != null && this.config.getBoolean(ConfigKeys.MODERATION_LOGGING_ENABLED);
	}
}
