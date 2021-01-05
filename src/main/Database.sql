-- Spring Framework

select dbms_xdb.gethttpport() from dual;



-- 사용자 만들기 & 권한 설정

drop user spring cascade;

create user spring identified by spring;
grant connect, resource to spring;

drop table tbl_board;
drop sequence seq_board;

--create sequence seq_board;
create SEQUENCE seq_board
start WITH 0 
minvalue 0 
INCREMENT by 1;


create table tbl_board(
    bno number(10, 0) primary key,
    title varchar2(200) not null,
    content varchar2(2000) not null,
    writer varchar2(50) not null,
    regdate date default sysdate,
    updatedate date default sysdate
);

insert into tbl_board (bno, title, content, writer) values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user00');
insert into tbl_board (bno, title, content, writer) values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user01');
insert into tbl_board (bno, title, content, writer) values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user02');
insert into tbl_board (bno, title, content, writer) values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user03');
insert into tbl_board (bno, title, content, writer) values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user04');


select * from tbl_board;

select * from tbl_board order by bno desc;


-- 재귀 복사를 통해 배수로 실행한다.
insert into tbl_board (bno, title, content, writer)
(select seq_board.nextval, title, content, writer from tbl_board);

select count(*) from tbl_board;
select * from tbl_board order by bno desc;









