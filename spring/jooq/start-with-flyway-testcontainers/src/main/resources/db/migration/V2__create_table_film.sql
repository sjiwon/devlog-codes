CREATE TABLE IF NOT EXISTS film
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
    `title`        VARCHAR(100) NOT NULL,
    `description`  VARCHAR(100) NOT NULL,
    `release_date` DATE         NOT NULL,
    `length`       INT          NOT NULL
) ENGINE = InnoDB
  CHARSET = UTF8MB4;
