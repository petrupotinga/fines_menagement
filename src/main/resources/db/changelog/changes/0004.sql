-- db/changelog/changes/0004-add-idnp-birthdate.sql
ALTER TABLE owner
ADD COLUMN idnp VARCHAR(255) NOT NULL;

ALTER TABLE owner
ADD COLUMN birth_date VARCHAR(255) NOT NULL;
