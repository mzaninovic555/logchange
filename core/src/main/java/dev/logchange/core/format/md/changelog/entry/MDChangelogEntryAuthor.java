package dev.logchange.core.format.md.changelog.entry;

import dev.logchange.core.format.md.MD;
import dev.logchange.core.model.ChangelogEntryAuthor;
import net.steppschuh.markdowngenerator.link.Link;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;

class MDChangelogEntryAuthor implements MD {

    private static final String authorFormat = "(${author})";
    private static final String AT = "@";
    private static final String LINK = "LINK";

    private final ChangelogEntryAuthor author;

    public MDChangelogEntryAuthor(ChangelogEntryAuthor author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return getAuthor();
    }

    public String getAuthor() {
        if (StringUtils.isNotBlank(author.getName()) || StringUtils.isNotBlank(author.getNick()) || StringUtils.isNotBlank(author.getUrl())) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("author", (getAuthorName() + " " + getAuthorNick() + " " + getLinkWhenNameEmpty()).trim());
            StringSubstitutor sub = new StringSubstitutor(valuesMap);
            return sub.replace(authorFormat);
        }
        return StringUtils.EMPTY;
    }

    private String getAuthorName() {
        if (StringUtils.isNotBlank(author.getUrl()) && StringUtils.isNotBlank(author.getName())) {
            return new Link(author.getName(), author.getUrl()).toString();
        }

        return StringUtils.defaultString(author.getName());
    }

    private String getAuthorNick() {
        if (StringUtils.isBlank(author.getNick())) {
            return StringUtils.EMPTY;
        }

        return AT + author.getNick();
    }

    private String getLinkWhenNameEmpty() {
        if (StringUtils.isNotBlank(author.getName())) {
            return StringUtils.EMPTY;
        }

        if (StringUtils.isNotBlank(author.getUrl())) {
            return new Link(LINK, author.getUrl()).toString();
        }

        return StringUtils.EMPTY;
    }
}
