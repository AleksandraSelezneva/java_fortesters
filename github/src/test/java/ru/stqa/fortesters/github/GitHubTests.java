package ru.stqa.fortesters.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GitHubTests {

    @Test
    public void testCommits() throws IOException {
        Github github = new RtGithub("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"); //установление соединеня с GitHub через удаленный API
        RepoCommits commits = github.repos().get(new Coordinates
                .Simple("AleksandraSelezneva", "java_fortesters")).commits();
        //в метод передаем набор пар, кот.опсывают условия набора коммитов
        //нам нужны все, поэтому построим пустой набор пар
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}
