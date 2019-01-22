package com.hopiestra.site.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A InternationalArticle.
 */
@Entity
@Table(name = "international_article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InternationalArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @NotNull
    private Language language;

    @ManyToOne(optional = false)
    @NotNull
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public InternationalArticle title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public InternationalArticle content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Language getLanguage() {
        return language;
    }

    public InternationalArticle language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Article getArticle() {
        return article;
    }

    public InternationalArticle article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternationalArticle internationalArticle = (InternationalArticle) o;
        if (internationalArticle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), internationalArticle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InternationalArticle{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
