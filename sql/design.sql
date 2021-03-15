CREATE DATABASE demo;
CREATE TABLE t_user (
    id bigint not null primary key auto_increment,
    username char(128) unique not null,
    phonenumber bigint unique not null,
    password_digest varchar(128) not null,
    age int unsigned not null,
    active int default 1, # 1 true 0 false
    gender int default 1, # 1 男 0 女
    avatar char(128) default '',
    groups text,  #当前用户的朋友id集合使用,分隔
    friends text, #当前用户的朋友id集合使用,分隔
    chats text, #当前用户的朋友id集合使用,分隔
    created_at timestamp default current_timestamp, 
    updated_at timestamp default current_timestamp on update current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_role (
	id bigint not null primary key auto_increment,
	name char(128) unique not null,
	count bigint not null default 0,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp on update current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_user_role (
	id bigint not null primary key auto_increment,
	user_id bigint not null references t_user(id),
	role_id bigint not null references t_role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_friendship (
	id bigint not null primary key auto_increment,
    user_a bigint not null references t_user(id),
    user_b bigint not null references t_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 讨论组
CREATE TABLE t_group (
	id bigint not null primary key auto_increment,
    owner_id bigint not null references t_user(id),
    owner_name char(20) not null,
    group_name char(20) not null,
    members text, # 当前讨论组内用户id的集合使用,分隔
    last_message char(255),
	updated_at timestamp default current_timestamp on update current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_invitation (
	id bigint not null primary key auto_increment,
    sender_id bigint not null references t_user(id),
    receiver_id bigint references t_user(id),
    group_id bigint references t_group(id),
    sender_name char(20) not null,
    group_name char(20) default '',
    invitation_type char(1) default '1', # 0 好友请求 1 群邀请
    agree char(1) default '1', # 0 拒绝 1 同意  
    handled char(1) default '0' # 0 未处理 1已处理
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_role (
	id bigint not null primary key auto_increment,
  rolename char(20) not null,
  user_count int not null default 0,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp on update current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_user_role (
	id bigint not null primary key auto_increment,
  user_id bigint not null references t_user(id) ,
  role_id bigint not null references t_role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_resource (
  id bigint not null primary key auto_increment,
  serialname char(255) unique not null,
  name char(255) not null,
  note text,
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp on update current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 用户讨论组关系表 
CREATE TABLE t_user_group (
	id bigint not null primary key auto_increment,
    user_id bigint not null references t_user(id),
    group_id bigint not null references t_group(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 消息
CREATE TABLE t_message(
	id bigint not null primary key  auto_increment,
    chat_id bigint not null references t_chat(id),
    owner_id bigint not null references t_user(id),
    sender_id bigint not null references t_user(id),
    content text not null,
    is_read char(1) default '0' # 0未读 1已读
) NGEINE=InnoDB DEFAULT CHARSET=utf8;
