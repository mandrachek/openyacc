package com.isroot.stash.plugin;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.scm.git.ref.GitAnnotatedTag;
import com.atlassian.bitbucket.user.Person;
import com.atlassian.bitbucket.user.SimplePerson;

/**
 * Minimal metadata required to verify a commit.
 */
public class YaccCommit {
    private final String id;
    private final Person committer;
    private final String message;
    private final boolean isMerge;

    public YaccCommit(Commit commit) {
        this.id = commit.getId();
        this.committer = new SimplePerson(commit.getCommitter().getName(),
                commit.getCommitter().getEmailAddress());
        this.message = commit.getMessage();
        this.isMerge = commit.getParents().size() > 1;
    }

    public YaccCommit(GitAnnotatedTag annotatedTag) {
        this.id = annotatedTag.getId();
        this.committer = annotatedTag.getTagger();
        this.message = annotatedTag.getMessage().orElse(null);
        this.isMerge = false;
    }

    /**
     * Construct a new commit instance.
     *
     * @param id Commit ID (eg, Git hash).
     * @param committer The committer.
     * @param message Git commit message.
     * @param isMerge true if merge commit
     */
    public YaccCommit (String id, SimplePerson committer, String message, boolean isMerge) {
        this.id = id;
        this.committer = committer;
        this.message = removeTrailingNewLine(message);
        this.isMerge = isMerge;
    }

    /**
     * sford: Removing the trailing newline is necessary after changing to JGit to get commit information to fix the
     * stash author name linking bug (see commit 3b5e8e0). The commit message returned by JGit has a trailing newline
     * which wasn't present when using the Stash API to get the message. This broke the commit message regex, so, this
     * was added to maintain the previous behavior.
     */
    private String removeTrailingNewLine(String str) {
        if(str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    /**
     * Return the git commit ID.
     *
     * @return Git commit ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Return the git committer identity associated with this commit.
     *
     * @return Git committer identity.
     */
    public Person getCommitter() {
        return committer;
    }

    /**
     * Return the commit message associated with this commit.
     *
     * @return Commit message.
     */
    public String getMessage() {
        return message;
    }

    public boolean isMerge() {
        return isMerge;
    }
}
