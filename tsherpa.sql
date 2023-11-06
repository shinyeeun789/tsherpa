DROP DATABASE tsherpa;

CREATE DATABASE tsherpa;

USE tsherpa;

-- 회원 테이블 생성
CREATE TABLE role(
	roleId INT PRIMARY KEY AUTO_INCREMENT,
	role VARCHAR(255) DEFAULT NULL
);

CREATE TABLE user(
	userId INT PRIMARY KEY AUTO_INCREMENT,
	active INT DEFAULT 0,
	loginId VARCHAR(255) NOT NULL,
	userName VARCHAR(255) NOT NULL,
	password VARCHAR(300) NOT NULL
);

CREATE TABLE user_role(
	userId INT NOT NULL,
	roleId INT NOT NULL,
	PRIMARY KEY (userId, roleId)
);

INSERT INTO user
VALUES(DEFAULT, DEFAULT, 'admin', '관리자', '$2a$10$KXY.EhEskta7wG/HvMSeZ.CQ4FuGQZOmaHTL2eZPnidD6AUvc.rUS');

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