package com.hopiestra.site.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Lob
    @Column(name = "image_blob", nullable = false)
    private byte[] imageBlob;

    @Column(name = "image_blob_content_type", nullable = false)
    private String imageBlobContentType;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public Image imageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
        return this;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return imageBlobContentType;
    }

    public Image imageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
        return this;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    public String getName() {
        return name;
    }

    public Image name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        Image image = (Image) o;
        if (image.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), image.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", imageBlob='" + getImageBlob() + "'" +
            ", imageBlobContentType='" + getImageBlobContentType() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
