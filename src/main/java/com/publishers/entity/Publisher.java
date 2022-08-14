package com.publishers.entity;

import javax.persistence.*;


/**
 * Represents publisher entity.
 *
 * @author Ana Peterlic
 */
@Entity(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The publisher's code.
     * Example: OvoOhY
     */
    @Column(name = "code")
    private String code;

    /**
     * The publisher's name.
     * Example: OvoOhYName
     */
    @Column(name = "name")
    private String name;

    /**
     * The location of the file.
     * Example: s3://some-special-bucket/production/publisher-data/OvoOhYe16904b4-b91f-404b-8c1c-a29db90edd29
     */
    @Column(name = "file")
    private String file;

    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}


