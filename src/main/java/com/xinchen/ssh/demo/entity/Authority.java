package com.xinchen.ssh.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "authority",catalog = "ssh")
public class Authority {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "authority_name",nullable = false)
    private String authorityName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}
