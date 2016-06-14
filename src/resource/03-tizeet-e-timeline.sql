
-- -----------------------------------------------------
-- Table `tizeet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tizeet` (
    `id` MEDIUMINT(8) NOT NULL auto_increment,
    `conteudo` VARCHAR(140) NOT NULL,
    `usuario_id` MEDIUMINT(8) UNSIGNED NOT NULL,
    `create_time` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_tizeet_usuario1_idx` (`usuario_id` ASC),
    CONSTRAINT `fk_tizeet_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
AUTO_INCREMENT=1;


-- -----------------------------------------------------
-- Table `timeline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timeline` (
    `id` MEDIUMINT(8) NOT NULL auto_increment,
    `id_tzeet` MEDIUMINT(8) NOT NULL,
    `usuario_id` MEDIUMINT(8) UNSIGNED NOT NULL,    
    PRIMARY KEY (`id`),
    INDEX `lnk_timeline_tizeet` (`id_tzeet` ASC),
    INDEX `fk_timeline_usuario1_idx` (`usuario_id` ASC),
    CONSTRAINT `lnk_timeline_tizeet`
    FOREIGN KEY (`id_tzeet`)
    REFERENCES `tizeet` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_timeline_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
AUTO_INCREMENT=1;
