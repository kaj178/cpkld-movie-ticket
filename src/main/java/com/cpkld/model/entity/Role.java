package com.cpkld.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role", schema = "public")
public class Role {
    @Id
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name")
    private String roleName;

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    // private List<Account> accounts = new ArrayList<>();

    // public List<Account> getAccounts() {
    //     return accounts;
    // }

    // public void setAccounts(List<Account> accounts) {
    //     this.accounts = accounts;
    // }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role [roleId=" + roleId + ", roleName=" + roleName + "]";
    }
}
