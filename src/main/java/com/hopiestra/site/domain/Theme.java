package com.hopiestra.site.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
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

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "background_picture")
    private byte[] backgroundPicture;

    @Column(name = "background_picture_content_type")
    private String backgroundPictureContentType;

    @Column(name = "jhi_order")
    private Integer order;

    @ManyToOne
    private Theme parentTheme;

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

    public Theme name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBackgroundPicture() {
        return backgroundPicture;
    }

    public Theme backgroundPicture(byte[] backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
        return this;
    }

    public void setBackgroundPicture(byte[] backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }

    public String getBackgroundPictureContentType() {
        return backgroundPictureContentType;
    }

    public Theme backgroundPictureContentType(String backgroundPictureContentType) {
        this.backgroundPictureContentType = backgroundPictureContentType;
        return this;
    }

    public void setBackgroundPictureContentType(String backgroundPictureContentType) {
        this.backgroundPictureContentType = backgroundPictureContentType;
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
            ", name='" + getName() + "'" +
            ", backgroundPicture='" + getBackgroundPicture() + "'" +
            ", backgroundPictureContentType='" + getBackgroundPictureContentType() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
