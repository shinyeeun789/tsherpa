CREATE DATABASE pro05;

USE pro05;

-- 회원 테이블 생성
CREATE TABLE user(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL,
	password VARCHAR(300) NOT NULL,
	username VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	addr1 VARCHAR(300) NOT NULL,
	addr2 VARCHAR(300),
	postcode VARCHAR(30) NOT NULL,
	tel VARCHAR(20),
	birth DATE NOT NULL,
	bank VARCHAR(50),
	accountNum VARCHAR(100),
	regdate DATETIME DEFAULT CURRENT_TIMESTAMP,
	lev VARCHAR(20) DEFAULT 'USER',
	act VARCHAR(20) DEFAULT 'JOIN',
	CONSTRAINT key_name UNIQUE(name)
);

SELECT * FROM user;

INSERT INTO user
VALUES(DEFAULT, 'admin', '$2a$10$KXY.EhEskta7wG/HvMSeZ.CQ4FuGQZOmaHTL2eZPnidD6AUvc.rUS', '관리자', 'admin@edu.com', NULL, NULL, NULL, NULL, DEFAULT, 'ADMIN', DEFAULT);

INSERT INTO user
VALUES(DEFAULT, 'kimname', '$2a$10$KXY.EhEskta7wG/HvMSeZ.CQ4FuGQZOmaHTL2eZPnidD6AUvc.rUS', '김이름', 'kim@edu.com', NULL, NULL, NULL, NULL, DEFAULT, 'USER', DEFAULT);


drop table accountInfo;
-- 계좌 정보 테이블
CREATE TABLE accountInfo(
	ano INT PRIMARY KEY AUTO_INCREMENT,			-- 계좌 정보 번호
	id INT NOT NULL,									-- 사용자 아이디
	bank VARCHAR(100) NOT NULL,					-- 은행명
	accountNum VARCHAR(1000) NOT NULL,			-- 계좌번호
	repAccount BOOLEAN DEFAULT FALSE,			-- 대표계좌여부
	FOREIGN KEY (id) REFERENCES user(id)
);


-- 커뮤니티 카테고리 테이블 생성
CREATE TABLE category(
   cate VARCHAR(5) PRIMARY KEY NOT NULL,
   cateName VARCHAR(100) NOT NULL);

-- 카테고리 테이블 데이터
INSERT INTO category VALUES('A', '교과지도');
INSERT INTO category VALUES('B', '학습지도');
INSERT INTO category VALUES('C', '생활지도');
INSERT INTO category VALUES('D', '학급운영');
INSERT INTO category VALUES('E', '업무자료');


-- 게시판 테이블 생성
CREATE TABLE free(
	fno INT PRIMARY KEY AUTO_INCREMENT,
	cate VARCHAR(5) NOT NULL,
	name VARCHAR(20) NOT NULL,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(2000) NOT NULL,
	resdate TIMESTAMP DEFAULT CURRENT_TIME,
	views INT DEFAULT 0,
	recommend INT DEFAULT 0,
	FOREIGN KEY (cate) REFERENCES category(cate)
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

-- 거래 카테고리 테이블 생성
CREATE TABLE tradeCategory (
	cno INT AUTO_INCREMENT PRIMARY KEY,
	tradeCate VARCHAR(50) NOT NULL
);

-- 거래 카테고리 더미데이터 입력
INSERT INTO tradeCategory VALUES(DEFAULT, '학습교구');
INSERT INTO tradeCategory VALUES(DEFAULT, '문구');
INSERT INTO tradeCategory VALUES(DEFAULT, '보드게임');
INSERT INTO tradeCategory VALUES(DEFAULT, '학급운영');
INSERT INTO tradeCategory VALUES(DEFAULT, '기자재');


-- 거래 게시글 테이블 생성
CREATE TABLE trade (
	tno INT PRIMARY KEY AUTO_INCREMENT,
	cno INT NOT NULL,
	title VARCHAR(100) NOT NULL,
	name VARCHAR(20) NOT NULL,
	resdate DATE DEFAULT CURRENT_DATE,
	location VARCHAR(100) NOT NULL,
	tradeType VARCHAR(50) NOT NULL,
	price INT NOT NULL,
	deliveryFee INT NOT NULL,
	demage VARCHAR(50) NOT NULL,
	content VARCHAR(2000) NOT NULL,
	recommend INT DEFAULT 0,
	itemImg VARCHAR(500) NOT NULL,
	state VARCHAR(50) DEFAULT '판매중',
	FOREIGN KEY (cno) REFERENCES tradeCategory(cno),
	FOREIGN KEY (name) REFERENCES user(name)
);

-- 사용자 거래 정보 테이블
CREATE TABLE userRating (
	uid INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	rating INT DEFAULT 0,
	trustTrade BOOLEAN DEFAULT FALSE,
	content VARCHAR(2000),
	FOREIGN KEY (NAME) REFERENCES user(NAME)
);

-- 찜 목록 테이블
CREATE TABLE traderecommends(
	rno INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	tno INT NOT NULL,
	FOREIGN KEY (NAME) REFERENCES user(NAME),
	FOREIGN KEY (tno) REFERENCES trade(tno)
);


-- 결제 테이블 생성
CREATE TABLE payment(
	pno INT PRIMARY KEY AUTO_INCREMENT,
	tno INT NOT NULL,
	buyer VARCHAR(20) NOT NULL,
	impUid VARCHAR(100) NOT NULL,
	merchantUid VARCHAR(100) NOT NULL,
	applyNum VARCHAR(100) NOT NULL,
	FOREIGN KEY (tno) REFERENCES trade(tno),
	FOREIGN KEY (buyer) REFERENCES user(NAME)
);

-- 배송 테이블 생성
CREATE TABLE delivery(
	dno INT PRIMARY KEY AUTO_INCREMENT,
	pno INT NOT NULL,
	addrress VARCHAR(500) NOT NULL,
	dname VARCHAR(100),
	dtel VARCHAR(13),
	dstate VARCHAR(50) DEFAULT '결제 완료',
	arrivalDate DATE
);

SELECT * FROM payment;
SELECT * FROM delivery;
SELECT * FROM trade;