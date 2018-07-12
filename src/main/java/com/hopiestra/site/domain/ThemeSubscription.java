package com.hopiestra.site.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ThemeSubscription.
 */
@Entity
@Table(name = "theme_subscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ThemeSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = "(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "subscripiton_for_all")
    private Boolean subscripitonForAll;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "theme_subscription_themes",
               joinColumns = @JoinColumn(name="theme_subscriptions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="themes_id", referencedColumnName="id"))
    private Set<Theme> themes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public ThemeSubscription email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isSubscripitonForAll() {
        return subscripitonForAll;
    }

    public ThemeSubscription subscripitonForAll(Boolean subscripitonForAll) {
        this.subscripitonForAll = subscripitonForAll;
        return this;
    }

    public void setSubscripitonForAll(Boolean subscripitonForAll) {
        this.subscripitonForAll = subscripitonForAll;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public ThemeSubscription themes(Set<Theme> themes) {
        this.themes = themes;
        return this;
    }

    public ThemeSubscription addThemes(Theme theme) {
        this.themes.add(theme);
        theme.getThemeSubscriptions().add(this);
        return this;
    }

    public ThemeSubscription removeThemes(Theme theme) {
        this.themes.remove(theme);
        theme.getThemeSubscriptions().remove(this);
        return this;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
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
        ThemeSubscription themeSubscription = (ThemeSubscription) o;
        if (themeSubscription.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), themeSubscription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThemeSubscription{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", subscripitonForAll='" + isSubscripitonForAll() + "'" +
            "}";
    }
}
