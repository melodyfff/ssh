package com.xinchen.ssh.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "i18nResource",catalog = "ssh")
public class I18nResource  implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "language",nullable = false)
    private String language;

    @Column(name = "content",nullable = false)
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
