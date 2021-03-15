CREATE TABLE t_resource (
  id bigint not null primary key auto_increment,
  serialname char(255) unique not null,
  filename char(255) not null,
  note text,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp on update current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

select 
  id, serialname, 
  filename, note,
  created_at, updated_at 
from t_resource; 

insert into t_resource (
  id, serialname, filename, note
) values (
  #{id}, #{serialname}, #{filename}, #{note}
)