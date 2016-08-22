package com.leoman.sensitivewords.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_sensitive_words")
@Entity
public class SensitiveWords extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "words")
    private String words;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
