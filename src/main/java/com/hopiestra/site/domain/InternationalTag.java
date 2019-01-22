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
 * A InternationalTag.
 */
@Entity
@Table(name = "international_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InternationalTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(optional = false)
    @NotNull
    private Language language;

    @ManyToMany(mappedBy = "internationalTags")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tag> tags = new HashSet<>();

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

    public InternationalTag title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Language getLanguage() {
        return language;
    }

    public InternationalTag language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public InternationalTag tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public InternationalTag addTags(Tag tag) {
        this.tags.add(tag);
        tag.getInternationalTags().add(this);
        return this;
    }

    public InternationalTag removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.getInternationalTags().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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
        InternationalTag internationalTag = (InternationalTag) o;
        if (internationalTag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), internationalTag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InternationalTag{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
