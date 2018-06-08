package io.choerodon.gitlab.app.service.impl;

import java.util.List;
import java.util.Map;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.stereotype.Service;
import scala.Int;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.ProjectService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class ProjectServiceImpl implements ProjectService {

    private Gitlab4jClient gitlab4jclient;

    public ProjectServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }


    @Override
    public Project createProject(Integer groupId, String projectName, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            Project project = gitLabApi.getProjectApi().createProject(groupId, projectName);
            gitLabApi.getRepositoryApi().createBranch(project.getId(), "develop", "master");
            //设置默认分支
            project.setDefaultBranch("develop");
            return gitLabApi.getProjectApi().updateProject(project);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void deleteProject(Integer projectId, Integer userId) {
        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getProjectApi().deleteProject(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> createVariable(Integer projectId, String key, String value, boolean protecteds, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().addVariable(projectId, key, value, protecteds);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> createProtectedBranches(Integer projectId, String name, String mergeAccessLevel, String pushAccessLevel, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().protectedBranches(projectId, name, mergeAccessLevel, pushAccessLevel);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Project updateProject(Integer projectId, Integer userId) {
        try {
            Project project = gitlab4jclient.getGitLabApi().getProjectApi().getProject(projectId);
            project.setDefaultBranch("develop");
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().updateProject(project);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> queryBranchByBranchName(Integer id, String name, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().getBranch(id, name);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> listBranch(Integer id, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().getBranchs(id);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void deleteByBranchName(Integer id, String name, Integer userId) {
        try {
            gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().deleteBranch(id, name);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }
}