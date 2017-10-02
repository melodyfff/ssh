package com.xinchen.ssh.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "role", catalog = "ssh")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;


    @Column(name = "role_name", nullable = false)
    private String roleName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
