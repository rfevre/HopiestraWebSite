package com.hopiestra.site.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A InternationalTheme.
 */
@Entity
@Table(name = "international_theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InternationalTheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private Language language;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Theme theme;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public InternationalTheme name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public InternationalTheme language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Theme getTheme() {
        return theme;
    }

    public InternationalTheme theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
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
        InternationalTheme internationalTheme = (InternationalTheme) o;
        if (internationalTheme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), internationalTheme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InternationalTheme{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
