create database triangle;

use triangle;

create table `file`(
	  `id` int NOT NULL AUTO_INCREMENT
    , `img_id` varchar(11) NOT NULL
    , `original_file_url` varchar(128) NOT NULL
    , `last_retrieverd` timestamp NOT NULL
    , `retrieve_target` bool default false not null
    , primary key (`id`)
);