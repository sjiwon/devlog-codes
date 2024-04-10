CREATE TABLE IF NOT EXISTS film_actor
(
    `id`       BIGINT AUTO_INCREMENT PRIMARY KEY,
    `actor_id` BIGINT NOT NULL,
    `film_id`  BIGINT NOT NULL
) ENGINE = InnoDB
  CHARSET = UTF8MB4;

ALTER TABLE film_actor
    ADD CONSTRAINT fk_film_actor_actor_id_from_actor
        FOREIGN KEY (actor_id)
            REFERENCES actor (id);

ALTER TABLE film_actor
    ADD CONSTRAINT fk_film_actor_film_id_from_film
        FOREIGN KEY (film_id)
            REFERENCES film (id);
