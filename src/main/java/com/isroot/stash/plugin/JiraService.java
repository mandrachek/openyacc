package com.isroot.stash.plugin;

import com.isroot.stash.plugin.errors.YaccError;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Service object to interact with JIRA.
 *
 * @author Sean Ford
 * @since 2013-10-26
 */
public interface JiraService {
    boolean doesJiraApplicationLinkExist();
    List<YaccError> doesIssueMatchJqlQuery(String jqlQuery, IssueKey issueKey);
    List<YaccError> doesIssueExist(IssueKey issueKey);
    boolean doesProjectExist(IssueKey issueKey);
    List<String> checkJqlQuery(@Nonnull String jqlQuery);
}
