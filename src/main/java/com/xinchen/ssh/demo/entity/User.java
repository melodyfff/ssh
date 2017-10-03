package com.xinchen.ssh.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user", catalog = "ssh")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "register_time")
    private Date registrTime;


//     在单向关系中没有mappedBy,主控方相当于拥有指向另一方的外键的一方。
//        1.一对一和多对一的@JoinColumn注解的都是在“主控方”，都是本表指向外表的外键名称。
//        2.一对多的@JoinColumn注解在“被控方”，即一的一方，指的是外表中指向本表的外键名称。
//        3.多对多中，joinColumns写的都是本表在中间表的外键名称，
//             inverseJoinColumns写的是另一个表在中间表的外键名称。

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)//使用hibernate注解级联保存和更新
    @JoinTable(name = "user_role",catalog = "ssh",
    joinColumns = {@JoinColumn(name = "user_id",nullable = false)},
    inverseJoinColumns = {@JoinColumn(name = "role_id",nullable = false)})
    private Set<Role> roleList = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrTime() {
        return (Date) registrTime.clone();
    }

    public void setRegistrTime(Date registrTime) {
        this.registrTime = (Date) registrTime.clone();
    }

    public Set<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", registrTime=" + registrTime +
                ", roleList=" + roleList +
                '}';
    }
}
