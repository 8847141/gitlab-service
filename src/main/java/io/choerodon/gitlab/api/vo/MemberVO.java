package io.choerodon.gitlab.api.vo;

import javax.validation.constraints.NotNull;

public class MemberVO {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer accessLevel;
    private String expiresAt;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
}
