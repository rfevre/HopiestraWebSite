package com.hopiestra.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "publication_date", nullable = false)
    private Instant publicationDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "delete_date")
    private Instant deleteDate;

    @NotNull
    @Column(name = "admin_title", nullable = false)
    private String adminTitle;

    @ManyToOne(optional = false)
    @NotNull
    private User author;

    @ManyToOne
    private Theme theme;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InternationalArticle> internationalsArticles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "article_tags",
               joinColumns = @JoinColumn(name="articles_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    private Image backgroundPicture;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public Article publicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Article updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Article creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getDeleteDate() {
        return deleteDate;
    }

    public Article deleteDate(Instant deleteDate) {
        this.deleteDate = deleteDate;
        return this;
    }

    public void setDeleteDate(Instant deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getAdminTitle() {
        return adminTitle;
    }

    public Article adminTitle(String adminTitle) {
        this.adminTitle = adminTitle;
        return this;
    }

    public void setAdminTitle(String adminTitle) {
        this.adminTitle = adminTitle;
    }

    public User getAuthor() {
        return author;
    }

    public Article author(User user) {
        this.author = user;
        return this;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public Theme getTheme() {
        return theme;
    }

    public Article theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Set<InternationalArticle> getInternationalsArticles() {
        return internationalsArticles;
    }

    public Article internationalsArticles(Set<InternationalArticle> internationalArticles) {
        this.internationalsArticles = internationalArticles;
        return this;
    }

    public Article addInternationalsArticle(InternationalArticle internationalArticle) {
        this.internationalsArticles.add(internationalArticle);
        internationalArticle.setArticle(this);
        return this;
    }

    public Article removeInternationalsArticle(InternationalArticle internationalArticle) {
        this.internationalsArticles.remove(internationalArticle);
        internationalArticle.setArticle(null);
        return this;
    }

    public void setInternationalsArticles(Set<InternationalArticle> internationalArticles) {
        this.internationalsArticles = internationalArticles;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Article tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Article addTags(Tag tag) {
        this.tags.add(tag);
        tag.getArticles().add(this);
        return this;
    }

    public Article removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.getArticles().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Image getBackgroundPicture() {
        return backgroundPicture;
    }

    public Article backgroundPicture(Image image) {
        this.backgroundPicture = image;
        return this;
    }

    public void setBackgroundPicture(Image image) {
        this.backgroundPicture = image;
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
        Article article = (Article) o;
        if (article.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", deleteDate='" + getDeleteDate() + "'" +
            ", adminTitle='" + getAdminTitle() + "'" +
            "}";
    }
}
