create table tablelist(
tadirid varchar2(32),
taid varchar2(32) primary key,
taname varchar2(1000),
tastdate date,
taendate date,
tapubdate date,
tafinicount number(10),
tafrom varchar2(32),
tatype varchar2(45));

create table acounts(
acorgid varchar2(32),
acname varchar2(50) primary key,
acpwd varchar2(50),
acowner varchar2(50),
acemail varchar2(50),
acphone varchar2(50),
accdate date,
acstatu bit,
acdetail varchar2(4000));

create table tabledir(
dirid char(32) primary key,
ownername varchar2(50),
text varchar2(200),
pid varchar2(32),
leaf bit);

create table deals(
deid varchar2(32) primary key,
tadeid varchar2(32),
destatu bit,
acdeid varchar2(32));

create table organize(
orgid varchar2(32) primary key,
text varchar2(255),
pid varchar2(32),
leaf bit);