package com.xinchen.ssh.demo.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role", catalog = "ssh")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;


    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)//使用hibernate注解级联保存和更新
    @JoinTable(name = "user_role", catalog = "ssh",
            joinColumns = { @JoinColumn(name = "role_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false) })
    private Set<User> userList = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority", catalog = "ssh",
            joinColumns = { @JoinColumn(name = "role_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "authority_id", nullable = false) })
    private Set<Authority> authorityList = new HashSet<>();

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

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    public Set<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(Set<Authority> authorityList) {
        this.authorityList = authorityList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", authorityList=" + authorityList +
                '}';
    }
}
