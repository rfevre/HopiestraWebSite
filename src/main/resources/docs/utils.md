# HopiestraWebSite Documentations

## Génération d'une entitée

Pour générer une entitée et tous les services s'y rapportant :

    jhipster entity [name]

## Rendre un champ unique

Dans le fichier "added_entity_[name].xml" ajouter la contrainte suivante sur la colonne :

    <constraints nullable="false" unique="true" uniqueConstraintName="uk_tag_name" />

Dans l'objet java de l'entity ajouter l'annotation suivante :

    @Column(name = "[name]", nullable = false, unique = true)

## Importer un css

Pour importer un css, il suffit de rajouter un import de celui-ci dans le fichier "vendor.css".
