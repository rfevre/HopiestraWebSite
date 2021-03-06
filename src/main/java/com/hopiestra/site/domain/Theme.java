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
 * A Theme.
 */
@Entity
@Table(name = "theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_order")
    private Integer order;

    @NotNull
    @Column(name = "admin_title", nullable = false, unique = true)
    private String adminTitle;

    @ManyToOne
    private Theme parentTheme;

    @ManyToMany(mappedBy = "themes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ThemeSubscription> themeSubscriptions = new HashSet<>();

    @ManyToOne
    private Image backgroundPicture;

    @OneToMany(mappedBy = "theme")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InternationalTheme> internationalThemes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public Theme order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getAdminTitle() {
        return adminTitle;
    }

    public Theme adminTitle(String adminTitle) {
        this.adminTitle = adminTitle;
        return this;
    }

    public void setAdminTitle(String adminTitle) {
        this.adminTitle = adminTitle;
    }

    public Theme getParentTheme() {
        return parentTheme;
    }

    public Theme parentTheme(Theme theme) {
        this.parentTheme = theme;
        return this;
    }

    public void setParentTheme(Theme theme) {
        this.parentTheme = theme;
    }

    public Set<ThemeSubscription> getThemeSubscriptions() {
        return themeSubscriptions;
    }

    public Theme themeSubscriptions(Set<ThemeSubscription> themeSubscriptions) {
        this.themeSubscriptions = themeSubscriptions;
        return this;
    }

    public Theme addThemeSubscriptions(ThemeSubscription themeSubscription) {
        this.themeSubscriptions.add(themeSubscription);
        themeSubscription.getThemes().add(this);
        return this;
    }

    public Theme removeThemeSubscriptions(ThemeSubscription themeSubscription) {
        this.themeSubscriptions.remove(themeSubscription);
        themeSubscription.getThemes().remove(this);
        return this;
    }

    public void setThemeSubscriptions(Set<ThemeSubscription> themeSubscriptions) {
        this.themeSubscriptions = themeSubscriptions;
    }

    public Image getBackgroundPicture() {
        return backgroundPicture;
    }

    public Theme backgroundPicture(Image image) {
        this.backgroundPicture = image;
        return this;
    }

    public void setBackgroundPicture(Image image) {
        this.backgroundPicture = image;
    }

    public Set<InternationalTheme> getInternationalThemes() {
        return internationalThemes;
    }

    public Theme internationalThemes(Set<InternationalTheme> internationalThemes) {
        this.internationalThemes = internationalThemes;
        return this;
    }

    public Theme addInternationalThemes(InternationalTheme internationalTheme) {
        this.internationalThemes.add(internationalTheme);
        internationalTheme.setTheme(this);
        return this;
    }

    public Theme removeInternationalThemes(InternationalTheme internationalTheme) {
        this.internationalThemes.remove(internationalTheme);
        internationalTheme.setTheme(null);
        return this;
    }

    public void setInternationalThemes(Set<InternationalTheme> internationalThemes) {
        this.internationalThemes = internationalThemes;
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
        Theme theme = (Theme) o;
        if (theme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), theme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Theme{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", adminTitle='" + getAdminTitle() + "'" +
            "}";
    }
}
