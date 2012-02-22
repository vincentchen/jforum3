package net.jforum.services;

import net.jforum.entities.Config;
import net.jforum.repository.ConfigDao;
import net.jforum.util.ConfigKeys;
import net.jforum.util.I18n;
import net.jforum.util.JForumConfig;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author Rafael Steil
 */
@Service
public class ConfigService {
	private final JForumConfig config;
	private final ConfigDao repository;
	private final I18n i18n;

	public ConfigService(JForumConfig config, ConfigDao repository, I18n i18n) {
		this.config = config;
		this.repository = repository;
		this.i18n = i18n;
	}

	public void save(HttpServletRequest request) {
		for (Enumeration<?> e = request.getParameterNames(); e.hasMoreElements(); ) {
			String key = (String) e.nextElement();

			if (key.startsWith("p_")) {
				String value = request.getParameter(key);

				String name = key.substring(key.indexOf('_') + 1);
				Config entry = this.repository.getByName(name);

				if (entry == null) {
					entry = new Config();
					entry.setName(name);
				}

				entry.setValue(value);

				this.config.clearProperty(name);
				this.config.setProperty(name, value);

				this.repository.update(entry);
			}
		}

		this.i18n.changeBoardDefaultLanguage(this.config.getValue(ConfigKeys.I18N_DEFAULT));
	}
}
