package com.hopiestra.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Article> articles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tag_international_tags",
               joinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="international_tags_id", referencedColumnName="id"))
    private Set<InternationalTag> internationalTags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Tag code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Tag articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Tag addArticles(Article article) {
        this.articles.add(article);
        article.getTags().add(this);
        return this;
    }

    public Tag removeArticles(Article article) {
        this.articles.remove(article);
        article.getTags().remove(this);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<InternationalTag> getInternationalTags() {
        return internationalTags;
    }

    public Tag internationalTags(Set<InternationalTag> internationalTags) {
        this.internationalTags = internationalTags;
        return this;
    }

    public Tag addInternationalTags(InternationalTag internationalTag) {
        this.internationalTags.add(internationalTag);
        internationalTag.getTags().add(this);
        return this;
    }

    public Tag removeInternationalTags(InternationalTag internationalTag) {
        this.internationalTags.remove(internationalTag);
        internationalTag.getTags().remove(this);
        return this;
    }

    public void setInternationalTags(Set<InternationalTag> internationalTags) {
        this.internationalTags = internationalTags;
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
        Tag tag = (Tag) o;
        if (tag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
