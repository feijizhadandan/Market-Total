-- auto-generated definition
create table book
(
    id          bigint           not null
        primary key,
    name        varchar(100)     not null comment '图书名字',
    type        varchar(100)     not null comment '图书类型',
    count       int              not null comment '图书数量',
    del_flag    char default '0' null comment '删除标志 0：存在  2：删除',
    create_by   bigint           null comment '创建者',
    create_time datetime         not null comment '创建时间',
    update_by   bigint           null comment '更新者',
    update_time datetime         not null comment '更新时间',
    version     bigint           not null comment '版本'
);

-- auto-generated definition
create table sys_menu
(
    id          bigint auto_increment
        primary key,
    menu_name   varchar(64)  default 'NULL' not null comment '菜单名',
    path        varchar(200)                null comment '路由地址',
    component   varchar(255)                null comment '组件路径',
    visible     char         default '0'    null comment '菜单状态（0显示 1隐藏）',
    status      char         default '0'    null comment '菜单状态（0正常 1停用）',
    perms       varchar(100)                null comment '权限标识',
    icon        varchar(100) default '#'    null comment '菜单图标',
    create_by   bigint                      null,
    create_time datetime                    null,
    update_by   bigint                      null,
    update_time datetime                    null,
    del_flag    char         default '0'    null comment '是否删除（0未删除 1已删除）',
    version     bigint       default 1      null comment '版本',
    remark      varchar(500)                null comment '备注'
)
    comment '菜单表';

-- auto-generated definition
create table sys_role
(
    id          bigint auto_increment
        primary key,
    name        varchar(128)       null,
    role_key    varchar(100)       null comment '角色权限字符串',
    status      char   default '0' null comment '角色状态（0正常 1停用）',
    del_flag    char   default '0' null comment 'del_flag',
    create_by   bigint             null,
    create_time datetime           null,
    update_by   bigint             null,
    update_time datetime           null,
    version     bigint default 1   null comment '版本',
    remark      varchar(500)       null comment '备注'
)
    comment '角色表';

-- auto-generated definition
create table sys_role_menu
(
    role_id bigint not null comment '角色ID',
    menu_id bigint not null comment '菜单id',
    primary key (role_id, menu_id)
);

-- auto-generated definition
create table sys_user
(
    id          bigint                   not null comment '用户ID'
        primary key,
    user_name   varchar(64)              not null comment '用户账号',
    nick_name   varchar(64)              not null comment '用户昵称',
    user_type   varchar(2)  default '00' null comment '用户类型（00系统用户）',
    email       varchar(64) default ''   null comment '用户邮箱',
    phone       varchar(11) default ''   null comment '手机号码',
    sex         char        default '0'  null comment '用户性别（0男 1女 2未知）',
    password    varchar(100)             not null comment '密码',
    status      char        default '0'  null comment '帐号状态（0正常 1停用）',
    del_flag    char        default '0'  null comment '删除标志（0代表存在 1代表删除）',
    create_by   bigint                   null comment '创建者',
    create_time datetime                 null comment '创建时间',
    update_by   bigint                   null comment '更新者',
    update_time datetime                 null comment '更新时间',
    version     bigint      default 1    null comment '版本'
);

-- auto-generated definition
create table sys_user_role
(
    user_id bigint not null comment '用户id',
    role_id bigint not null comment '角色id',
    primary key (user_id, role_id)
);

