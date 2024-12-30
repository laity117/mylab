create database web_lab;

use web_lab;

create table count (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       count int
);

insert into count (count) values (1);

create table password (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          password int
);

insert into password (password) values ('1');

insert into count (count) values (1);

CREATE TABLE user_profile (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(100) NOT NULL default 'a',
                              age INT CHECK (age >= 0 AND age <= 150) default 0,
                              job VARCHAR(100) not null default 'a',
                              phone VARCHAR(20) default 'a',
                              email VARCHAR(100) default 'a',
                              address TEXT,
                              profile_photo LONGBLOB default null,
                              content_type VARCHAR(255) default null
);

insert into user_profile (name, age, job, phone, email, address)
values ('赵艺阳', '19', '后端开发', '11111111111', '1111111111@qq.com', '长沙市');


CREATE TABLE education_background (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      date_range VARCHAR(50) default '',
                                      school_name VARCHAR(100) default '',
                                      degree_major VARCHAR(100) default ''
);

insert into education_background (date_range, school_name, degree_major)
values
    ('2022-2026', '中南大学', '计算机科学与技术')