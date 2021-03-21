use jspstudy;

create table tb_banapresso (
	ba_idx bigint auto_increment primary key,
    ba_name varchar(50) not null,
    ba_address varchar(200) not null
);
drop table tb_banapresso;
select * from tb_banapresso;

INSERT INTO tb_banapresso(ba_name, ba_address) VALUES ("수원점", "수원시 권선구"); 