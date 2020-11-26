CREATE SCHEMA `bookish`;
CREATE TABLE `bookish`.`book` (
  `isbn` VARCHAR(13) NOT NULL,
  `title` VARCHAR(45) NULL,
  PRIMARY KEY (`isbn`));
CREATE TABLE `bookish`.`cauthoropy` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_isbn` VARCHAR(13) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `copy_book`
    FOREIGN KEY (`book_isbn`)
    REFERENCES `bookish`.`book` (`isbn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE TABLE `bookish`.`genre` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `age_group` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);
CREATE TABLE `bookish`.`genre_book` (
  `genre_id` INT NOT NULL,
  `book_isbn` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`genre_id`, `book_isbn`),
  INDEX `book_idx` (`book_isbn` ASC) VISIBLE,
  CONSTRAINT `genrebook_genre`
    FOREIGN KEY (`genre_id`)
    REFERENCES `bookish`.`genre` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `genrebook_book`
    FOREIGN KEY (`book_isbn`)
    REFERENCES `bookish`.`book` (`isbn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE TABLE `bookish`.`author` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
CREATE TABLE `bookish`.`authorbook` (
  `author_id` INT NOT NULL,
  `book_isbn` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`author_id`, `book_isbn`),
  INDEX `book_isbn_idx` (`book_isbn` ASC) VISIBLE,
  CONSTRAINT `authorbook_author`
    FOREIGN KEY (`author_id`)
    REFERENCES `bookish`.`author` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `authorbook_book`
    FOREIGN KEY (`book_isbn`)
    REFERENCES `bookish`.`book` (`isbn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE TABLE `bookish`.`member` (
  `name` VARCHAR(255) NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));
  CREATE TABLE `bookish`.`loan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NOT NULL,
  `copy_id` INT NOT NULL,
  `issue_date` DATETIME NOT NULL,
  `return_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `loan_copy_idx` (`copy_id` ASC) VISIBLE,
  INDEX `loan_member_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `loan_copy`
    FOREIGN KEY (`copy_id`)
    REFERENCES `bookish`.`copy` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `loan_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `bookish`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
