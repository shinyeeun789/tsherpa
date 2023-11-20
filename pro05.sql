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

-- 계좌 정보 테이블
CREATE TABLE accountInfo(
	ano INT PRIMARY KEY AUTO_INCREMENT,			-- 계좌 정보 번호
	id INT NOT NULL,									-- 사용자 아이디
	bank VARCHAR(100) NOT NULL,					-- 은행명
	accountNum VARCHAR(1000) NOT NULL,			-- 계좌번호
	repAccount BOOLEAN DEFAULT FALSE,			-- 대표계좌여부
	FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
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
	FOREIGN KEY (cate) REFERENCES category(cate) ON DELETE CASCADE
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

-- 공지사항 테이블 생성
CREATE TABLE notice (
   seq INT AUTO_INCREMENT PRIMARY KEY,
   title VARCHAR(100) NOT NULL,
   content VARCHAR(1000) NOT NULL,
   nickname VARCHAR(20),
   regdate DATETIME DEFAULT CURRENT_TIMESTAMP(),
   visited INT DEFAULT 0
);


-- faq 테이블 생성
CREATE TABLE faq (
   fno INT  PRIMARY KEY AUTO_INCREMENT ,
   question VARCHAR(1000) NOT NULL,
   answer VARCHAR(1000) NOT NULL,
   cnt INT DEFAULT 0 NOT NULL
);

-- faq 더미 데이터 추가
INSERT INTO faq(question, answer) VALUES('웹사이트에 어떻게 가입하나요?', '회원 가입 페이지에서 필요한 정보를 입력하여 가입할 수 있습니다.');
INSERT INTO faq(question, answer) VALUES('게시물을 어떻게 작성하나요?', '커뮤니티 페이지의 "글 작성" 버튼을 클릭하여 게시물을 작성할 수 있습니다.');
INSERT INTO faq(question, answer) VALUES('다른 회원들의 게시물에 댓글을 달고 싶어요. 어떻게 해야 하나요?','게시물의 하단에 있는 "댓글 작성" 영역에 댓글을 입력하여 등록할 수 있습니다.');
INSERT INTO faq(question, answer) VALUES('제가 작성한 글이 갑자기 사라졌어요', '부적절한 내용은 관리자가 예고 없이 삭제할 수 있습니다. 커뮤니티 규칙을 지켜주세요:)');
INSERT INTO faq(question, answer) VALUES('개인 정보 보호 정책은 무엇인가요?', '웹사이트의 개인 정보 보호 정책은 "회원가입의 회원약관동의" 페이지에서 확인할 수 있습니다.');
INSERT INTO faq(question, answer) VALUES('문의사항이 있을 때 어떻게 연락할 수 있나요?', '고객지원의 "QnA" 페이지에서 문의글을 작성하여 문의할 수 있습니다.');
INSERT INTO faq(question, answer) VALUES('어떤 유형의 교육자료를 제공하나요?', '초등학교, 중학교 및 고등학교 학생들을 위한 교과서, 참고서 및 워크북을 포함한 다양한 교육자료를 제공합니다.');
INSERT INTO faq(question, answer) VALUES('책은 실물 및 디지털 형식으로 모두 제공되나요?', '네, 대부분의 책은 실물 및 디지털 형식으로 제공되며 다양한 학습 선호도를 고려합니다.');
INSERT INTO faq(question, answer) VALUES('책이나 주제에 대한 제안을 할 수 있나요?', '물론입니다! 저희는 모든 제안을 소중히 생각합니다. 고객 지원팀에 의견을 보내주시면 됩니다.');
INSERT INTO faq(question, answer) VALUES('책은 정기적으로 업데이트되나요?', '네, 정확성과 관련성을 보장하기 위해 최신 교과서 및 참고 자료의 최신 판을 제공하기 위해 노력하고 있습니다.');


-- QNA
CREATE TABLE qna(
  qno int PRIMARY KEY AUTO_INCREMENT,   			-- 번호
  title VARCHAR(100) NOT NULL,   					-- 제목
  content VARCHAR(1000) NOT NULL,   				-- 내용`
  author VARCHAR(16),   								-- 작성자
  resdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 등록일
  lev INT DEFAULT 0, 									-- 질문(0), 답변(1)
  par INT DEFAULT 0,													-- 질문(자신 레코드의 qno), 답변(질문의 글번호)
  FOREIGN KEY(author) REFERENCES user(name) ON DELETE CASCADE);

SELECT * FROM qna;

-- 자료실 테이블 생성
CREATE TABLE dataRoom (
  articleNo int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  id VARCHAR(20) NOT NULL,
  title varchar(100) NOT NULL,
  content varchar(2000) NOT NULL,
  regdate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- 업로드 된 파일 정보 테이블 생성
CREATE TABLE fileInfo(
  no int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  articleNo INT,
  saveFolder VARCHAR(300) NOT NULL,
  originFile VARCHAR(300) NOT NULL,
  saveFile VARCHAR(300) NOT NULL,
  FOREIGN KEY(articleNo) REFERENCES dataRoom(articleNo) ON DELETE CASCADE 
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
	FOREIGN KEY (cno) REFERENCES tradeCategory(cno) ON DELETE CASCADE,
	FOREIGN KEY (name) REFERENCES user(name) ON DELETE CASCADE
);


-- 사용자 거래 정보 테이블
CREATE TABLE userRating (
	uid INT AUTO_INCREMENT PRIMARY KEY,
	tno INT NOT NULL,
	seller VARCHAR(20) NOT NULL,
	buyer VARCHAR(20) NOT NULL,
	rating INT DEFAULT 0,
	trustTrade BOOLEAN DEFAULT FALSE,
	content VARCHAR(2000),
	FOREIGN KEY (tno) REFERENCES trade(tno) ON DELETE CASCADE,
	FOREIGN KEY (seller) REFERENCES user(NAME) ON DELETE CASCADE,
	FOREIGN KEY (buyer) REFERENCES user(NAME) ON DELETE CASCADE
);

-- 찜 목록 테이블
CREATE TABLE traderecommends(
	rno INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	tno INT NOT NULL,
	FOREIGN KEY (NAME) REFERENCES user(NAME) ON DELETE CASCADE,
	FOREIGN KEY (tno) REFERENCES trade(tno) ON DELETE CASCADE
);


-- 결제 테이블 생성
CREATE TABLE payment(
	pno INT PRIMARY KEY AUTO_INCREMENT,
	tno INT NOT NULL,
	buyer VARCHAR(20) NOT NULL,
	impUid VARCHAR(100),
	merchantUid VARCHAR(100),
	applyNum VARCHAR(100),
	price INT NOT NULL,
	FOREIGN KEY (tno) REFERENCES trade(tno) ON DELETE CASCADE,
	FOREIGN KEY (buyer) REFERENCES user(NAME) ON DELETE CASCADE
);

-- 배송 테이블 생성
CREATE TABLE delivery(
	dno INT PRIMARY KEY AUTO_INCREMENT,
	pno INT NOT NULL,
	address VARCHAR(500) NOT NULL,
	dcode VARCHAR(100), -- 송장번호
	dname VARCHAR(100),
	dtel VARCHAR(13),
	dstate VARCHAR(50) DEFAULT '결제 완료',
	arrivalDate DATE,
	FOREIGN KEY (pno) REFERENCES payment(pno) ON DELETE CASCADE
);

-- 채팅 테이블 생성
CREATE TABLE chatroom(
	roomId VARCHAR(200) PRIMARY key COMMENT '채팅방번호',
	name VARCHAR(200) COMMENT '채팅방이름',
	buyer VARCHAR(20) COMMENT '구매자',
	seller VARCHAR(20) COMMENT '판매자',
	tno int COMMENT '중고상품번호',
	act VARCHAR(20) DEFAULT 'JOIN' COMMENT '활성화상태', -- JOIN(활성), DSBLD(비활성)
	FOREIGN KEY(buyer) REFERENCES user(name) ON DELETE CASCADE,
	FOREIGN KEY(seller) REFERENCES user(name) ON DELETE CASCADE,
	FOREIGN KEY(tno) REFERENCES trade(tno) ON DELETE CASCADE
);

-- 채팅 메세지 테이블 생성
CREATE TABLE chatmsg(
	no BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '채팅메세지번호',
	roomId VARCHAR(200) NOT NULL COMMENT '채팅방번호',
	mtype VARCHAR(10) NOT NULL COMMENT '메세지타입',
	sender VARCHAR(20) NOT NULL COMMENT '보내는이',
	message VARCHAR(1000) NOT NULL COMMENT '내용',
	resdate DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
	FOREIGN KEY(sender) REFERENCES user(name) ON DELETE CASCADE,
	FOREIGN KEY(roomId) REFERENCES chatroom(roomId) ON DELETE CASCADE
);