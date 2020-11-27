DROP SCHEMA IF EXISTS `bookish`;
CREATE SCHEMA `bookish`;
CREATE TABLE `bookish`.`book` (
  `isbn` VARCHAR(13) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `author` VARCHAR(255) NOT NULL,
  `numberOfCopies` INT NOT NULL,
  PRIMARY KEY (`isbn`));
CREATE TABLE `bookish`.`member` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(255) NOT NULL,
  `secondName` VARCHAR(255) NOT NULL,
  `telephoneNumber` CHAR(11),
  `emailAddress` VARCHAR(100),
  PRIMARY KEY (`id`));
  CREATE TABLE `bookish`.`loan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NOT NULL,
  `book_isbn` VARCHAR(13) NOT NULL,
  `issue_date` DATETIME NOT NULL,
  `return_date` DATETIME NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `loan_book`
    FOREIGN KEY (`book_isbn`)
    REFERENCES `bookish`.`book` (`isbn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `loan_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `bookish`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE TABLE `bookish`.`technologies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `logoUrl` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
 
INSERT INTO `bookish`.`technologies`
(id, name, logoUrl)
VALUES
(1,'Java','https://pngimage.net/wp-content/uploads/2018/06/java-logo-png-transparent-background-7.png'),
(2,'Spring','https://www.logolynx.com/images/logolynx/98/980c5fe716efb66c936eebe1937d5489.png'),
(3,'Thymeleaf','https://raw.githubusercontent.com/thymeleaf/thymeleaf-dist/master/src/artwork/thymeleaf%202016/thymeleaf_logo_transparent.png'),
(4,'MySQL','https://upload.wikimedia.org/wikipedia/commons/b/b2/Database-mysql.svg'),
(5,'HTML','https://www.w3.org/html/logo/downloads/HTML5_Logo_256.png'),
(6,'CSS','https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/CSS3_logo_and_wordmark.svg/2000px-CSS3_logo_and_wordmark.svg.png');
