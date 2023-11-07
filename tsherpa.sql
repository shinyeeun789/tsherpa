DROP DATABASE tsherpa;

CREATE DATABASE tsherpa;

USE tsherpa;

-- 회원 테이블 생성
CREATE TABLE role(
	roleId INT PRIMARY KEY AUTO_INCREMENT,
	role VARCHAR(255) DEFAULT NULL
);
DROP TABLE USER;
CREATE TABLE user(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL,
	password VARCHAR(300) NOT NULL,
	username VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	address VARCHAR(300),
	tel VARCHAR(20),
	regdate DATETIME DEFAULT CURRENT_TIMESTAMP,
	lev VARCHAR(20) DEFAULT 'USER',
	act VARCHAR(20) DEFAULT 'JOIN',
	CONSTRAINT key_name UNIQUE(name)
);

CREATE TABLE user_role(
	userId INT NOT NULL,
	roleId INT NOT NULL,
	PRIMARY KEY (userId, roleId)
);

SELECT * FROM USER;

INSERT INTO user
VALUES(DEFAULT, 'admin', '$2a$10$KXY.EhEskta7wG/HvMSeZ.CQ4FuGQZOmaHTL2eZPnidD6AUvc.rUS', '관리자', 'admin@edu.com', NULL, NULL, DEFAULT, 'ADMIN', DEFAULT);

INSERT INTO user
VALUES(DEFAULT, 'kimname', '$2a$10$KXY.EhEskta7wG/HvMSeZ.CQ4FuGQZOmaHTL2eZPnidD6AUvc.rUS', '김이름', 'kim@edu.com', NULL, NULL, DEFAULT, 'USER', DEFAULT);

COMMIT;

-- 게시판 테이블 생성
CREATE TABLE free(
	fno INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(2000) NOT NULL,
	resdate TIMESTAMP DEFAULT CURRENT_TIME,
	views INT DEFAULT 0,
	recommend INT DEFAULT 0
);

-- 댓글 테이블 생성
CREATE TABLE free_com (
	cno INT PRIMARY KEY AUTO_INCREMENT,
	fno INT NOT NULL,
	name VARCHAR(20) NOT NULL,
	content VARCHAR(2000) NOT NULL,
	resdate TIMESTAMP DEFAULT CURRENT_TIME,
	recommend INT DEFAULT 0,
	FOREIGN KEY (fno) REFERENCES free(fno) ON DELETE CASCADE
);


SELECT cno, fno, name, content, 
case
	when DATEDIFF(NOW(), resdate) > 0
	then CONCAT(DATEDIFF(NOW(), resdate), '일 전')
	when TIMESTAMPDIFF(HOUR, resdate, NOW()) > 0
	then CONCAT(TIMESTAMPDIFF(HOUR, resdate, NOW()), '시간 전')
	ELSE CONCAT(TIMESTAMPDIFF(MINUTE, resdate, NOW()), '분 전')
END AS resdate, recommend
FROM free_com;