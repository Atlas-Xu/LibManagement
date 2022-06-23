create table books
(
    book_id        int auto_increment comment '图书id'
        primary key,
    category_id    int            null comment '分类id',
    book_name      varchar(128)   null comment '图书名称',
    book_code      varchar(128)   null comment '图书条码',
    book_place_num varchar(128)   null comment '书架号',
    book_author    varchar(64)    null comment '作者',
    book_product   varchar(128)   null comment '出版社',
    book_price     decimal(18, 2) null comment '价格',
    book_store     int default 0  null comment '库存'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.books (book_id, category_id, book_name, book_code, book_place_num, book_author, book_product, book_price, book_store) VALUES (2, 2, 'C++ Primer Plus', '9787115103352', '3楼社科26架2列', '[美]普拉塔（Prata,S.）', '人民邮电出版社', 99.00, 98);
INSERT INTO LibManagement.books (book_id, category_id, book_name, book_code, book_place_num, book_author, book_product, book_price, book_store) VALUES (3, 2, 'Head First Java（中文版）', '9787508344980', '3楼社科26架3列', '张浩（译）', '中国电力出版社', 60.00, 99);
INSERT INTO LibManagement.books (book_id, category_id, book_name, book_code, book_place_num, book_author, book_product, book_price, book_store) VALUES (4, 2, '数据结构与算法分析：Java语言描述', '9787111528395', '3楼社科26架2列', '马克·艾伦·维斯', ' 机械工业出版社', 69.00, 98);
INSERT INTO LibManagement.books (book_id, category_id, book_name, book_code, book_place_num, book_author, book_product, book_price, book_store) VALUES (5, 9, '孙子兵法', '9787101080759', '2楼文学11架3列', '孙武', '中华书局', 59.00, 99);
INSERT INTO LibManagement.books (book_id, category_id, book_name, book_code, book_place_num, book_author, book_product, book_price, book_store) VALUES (6, 7, '人力资源管理（第14版）', '9787300238449', '3楼社科12架5列', '（美）加里·德斯勒', '中国人民大学出版社', 99.00, 99);
INSERT INTO LibManagement.books (book_id, category_id, book_name, book_code, book_place_num, book_author, book_product, book_price, book_store) VALUES (7, 6, '科学探索者', '9787553601960', '3楼社科11架5列', '（美）帕迪利亚', '浙江教育出版社', 69.00, 97);
INSERT INTO LibManagement.books (book_id, category_id, book_name, book_code, book_place_num, book_author, book_product, book_price, book_store) VALUES (8, 3, '资治通鉴（精装全6册 汇评精注本）', '9787550273733', '2楼文学26架1列', '司马光', '北京联合出版公司', 99.00, 99);
INSERT INTO LibManagement.books (book_id, category_id, book_name, book_code, book_place_num, book_author, book_product, book_price, book_store) VALUES (9, 3, '全球通史：从史前史到21世纪', '9787301109489', '3楼社科21架3列', 'L·S·斯塔夫里阿诺斯', '北京大学出版社', 109.00, 10);
create table borrow_book
(
    borrow_id     int auto_increment comment '主键'
        primary key,
    book_id       int          null comment '图书id',
    reader_id     int          null comment '读者id',
    borrow_time   datetime     null comment '借书时间',
    return_time   datetime     null comment '还书时间',
    apply_status  varchar(2)   null comment '0: 待审核 1： 已审核  2：拒绝',
    borrow_status varchar(2)   null comment '0：审核中1:在借中  2：已还   3：拒绝',
    return_status varchar(2)   null comment '1: 正常还书 2：异常还书 3：丢失',
    excepion_text varchar(128) null comment '异常还书备注',
    apply_text    varchar(128) null comment '审核拒绝备注'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.borrow_book (borrow_id, book_id, reader_id, borrow_time, return_time, apply_status, borrow_status, return_status, excepion_text, apply_text) VALUES (66, 3, 7, '2022-01-08 16:58:03', '2022-01-12 00:00:00', '1', '2', '1', null, null);
INSERT INTO LibManagement.borrow_book (borrow_id, book_id, reader_id, borrow_time, return_time, apply_status, borrow_status, return_status, excepion_text, apply_text) VALUES (67, 4, 8, '2022-01-08 16:58:53', '2022-01-15 00:00:00', '1', '2', '1', null, null);
INSERT INTO LibManagement.borrow_book (borrow_id, book_id, reader_id, borrow_time, return_time, apply_status, borrow_status, return_status, excepion_text, apply_text) VALUES (68, 2, 7, '2022-01-08 17:15:38', '2022-01-10 00:00:00', '1', '1', null, null, null);
INSERT INTO LibManagement.borrow_book (borrow_id, book_id, reader_id, borrow_time, return_time, apply_status, borrow_status, return_status, excepion_text, apply_text) VALUES (69, 4, 6, '2022-01-08 18:22:27', '2022-01-30 00:00:00', '1', '1', null, null, null);
INSERT INTO LibManagement.borrow_book (borrow_id, book_id, reader_id, borrow_time, return_time, apply_status, borrow_status, return_status, excepion_text, apply_text) VALUES (70, 5, 6, '2022-01-11 17:48:21', '2022-01-28 00:00:00', '1', '1', null, null, null);
INSERT INTO LibManagement.borrow_book (borrow_id, book_id, reader_id, borrow_time, return_time, apply_status, borrow_status, return_status, excepion_text, apply_text) VALUES (71, 8, 8, '2022-01-11 17:52:51', '2022-01-29 00:00:00', '1', '1', null, null, null);
INSERT INTO LibManagement.borrow_book (borrow_id, book_id, reader_id, borrow_time, return_time, apply_status, borrow_status, return_status, excepion_text, apply_text) VALUES (72, 7, 8, '2022-01-11 17:52:51', '2022-01-29 00:00:00', '1', '1', null, null, null);
create table category
(
    category_id   int auto_increment comment '分类id'
        primary key,
    category_name varchar(64) null comment '分类名称',
    order_num     int         null comment '序号'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.category (category_id, category_name, order_num) VALUES (2, '计算机科学技术', 2);
INSERT INTO LibManagement.category (category_id, category_name, order_num) VALUES (3, '历史', 3);
INSERT INTO LibManagement.category (category_id, category_name, order_num) VALUES (6, '环境科学', 6);
INSERT INTO LibManagement.category (category_id, category_name, order_num) VALUES (7, '管理学', 7);
INSERT INTO LibManagement.category (category_id, category_name, order_num) VALUES (9, '中国军事', 9);
create table face
(
    face_id      int auto_increment comment '人脸id'
        primary key,
    face_feature blob     null comment '人脸特征',
    reader_id    int      null comment '读者id',
    update_time  datetime null comment '更新时间'
)
    collate = utf8mb4_bin;


create table menu
(
    menu_id     int auto_increment comment '菜单id'
        primary key,
    parent_id   int          null comment '父级菜单id',
    title       varchar(64)  null comment '菜单名称',
    code        varchar(36)  null comment '权限字段',
    name        varchar(36)  null comment '路由name',
    path        varchar(36)  null comment '路由path',
    url         varchar(128) null comment '组件路径',
    type        varchar(2)   null comment '类型(0 目录 1菜单，2按钮)',
    icon        varchar(36)  null comment '菜单图标',
    parent_name varchar(64)  null comment '上级菜单名称',
    order_num   int          null comment '序号',
    create_time datetime     null comment '创建时间',
    update_time datetime     null comment '更新时间'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (1, 0, '系统管理', 'sys:menu:setting', 'system', '/system', '', '0', 'el-icon-setting', '顶级菜单', 1, '2021-12-02 19:19:28', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (2, 1, '用户管理', 'sys:useList:index', 'sysUserList', '/UserList', '/system/UserList', '1', 'el-icon-s-check', '系统管理', 2, '2021-12-02 19:25:31', '2022-01-02 13:19:18');
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (3, 1, '角色管理', 'sys:sysRoleList:index', 'sysRoleList', '/RoleList', '/system/RoleList', '1', 'el-icon-s-claim', '系统管理', 3, '2021-12-02 19:27:25', '2022-01-02 13:19:43');
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (4, 1, '菜单管理', 'sys:menu:index', 'sysMenuList', '/MenuList', '/system/MenuList', '1', 'tree', '系统管理', 3, '2022-01-02 13:20:44', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (5, 2, '新增', 'sys:user:add', '', '', '', '2', '', '用户管理', 1, '2022-01-02 13:21:15', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (6, 2, '编辑', 'sys:user:edit', '', '', '', '2', '', '用户管理', 2, '2022-01-02 13:21:36', '2022-01-08 20:37:50');
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (7, 2, '删除', 'sys:user:delete', '', '', '', '2', '', '用户管理', 3, '2022-01-02 13:21:55', '2022-01-08 20:38:11');
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (8, 3, '新增', 'sys:role:add', '', '', '', '2', '', '角色管理', 1, '2022-01-02 13:22:21', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (9, 3, '编辑', 'sys:role:edit', '', '', '', '2', '', '角色管理', 2, '2022-01-02 13:22:38', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (10, 3, '删除', 'sys:role:delete', '', '', '', '2', '', '角色管理', 3, '2022-01-02 13:22:56', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (11, 4, '新增', 'sys:menu:add', '', '', '', '2', '', '菜单管理', 1, '2022-01-02 13:23:17', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (14, 0, '读者管理', 'sys:reader:root', 'reader', '/reader', '', '0', 'el-icon-user', '顶级菜单', 2, '2022-01-02 13:24:42', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (15, 14, '读者列表', 'sys:reader:list', 'readerList', '/readerList', '/reader/readerList', '1', 'el-icon-s-custom', '读者管理', 1, '2022-01-02 13:25:29', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (16, 15, '新增', 'sys:reader:add', '', '', '', '2', '', '读者列表', 1, '2022-01-02 13:25:47', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (17, 15, '编辑', 'sys:reader:edit', '', '', '', '2', '', '读者列表', 2, '2022-01-02 13:26:06', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (18, 15, '删除', 'sys:reader:delete', '', '', '', '2', '', '读者列表', 3, '2022-01-02 13:26:25', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (19, 0, '图书管理', 'sys:book:index', 'book', '/book', '', '0', 'el-icon-s-management', '顶级菜单', 3, '2022-01-02 13:27:11', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (20, 19, '图书分类', 'sys:bookCategory:index', 'bookCategory', '/bookCategory', '/book/bookCategory', '1', 'el-icon-files', '图书管理', 1, '2022-01-02 13:28:10', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (21, 19, '图书列表', 'sys:bookList:list', 'bookList', '/bookList', '/book/bookList', '1', 'el-icon-document', '图书管理', 2, '2022-01-02 13:28:59', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (22, 20, '新增', 'sys:bookCategory:add', '', '', '', '2', '', '图书分类', 1, '2022-01-02 13:29:31', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (23, 20, '编辑', 'sys:bookCategory:edit', '', '', '', '2', '', '图书分类', 2, '2022-01-02 13:29:52', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (24, 20, '删除', 'sys:bookCategory:delete', '', '', '', '2', '', '图书分类', 3, '2022-01-02 13:30:12', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (25, 21, '新增', 'sys:bookList:add', '', '', '', '2', '', '图书列表', 1, '2022-01-02 13:30:42', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (26, 21, '编辑', 'sys:bookList:edit', '', '', '', '2', '', '图书列表', 2, '2022-01-02 13:30:56', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (27, 21, '删除', 'sys:bookList:delete', '', '', '', '2', '', '图书列表', 3, '2022-01-02 13:31:10', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (28, 0, '借阅管理', 'sys:borrow:index', 'borrow', '/borrow', '', '0', 'el-icon-reading', '顶级菜单', 5, '2022-01-02 13:32:02', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (29, 28, '借书管理', 'sys:bookBorrow:index', 'bookBorrow', '/bookBorrow', '/borrow/bookBorrow', '1', 'el-icon-notebook-1', '借阅管理', 1, '2022-01-02 13:33:07', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (30, 28, '还书管理', 'sys:bookReturn:index', 'bookReturn', '/bookReturn', '/borrow/bookReturn', '1', 'el-icon-notebook-2', '借阅管理', 2, '2022-01-02 13:33:58', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (31, 28, '借阅查看', 'sys:borrowLook:index', 'borrowLook', '/borrowLook', '/borrow/borrowLook', '1', 'el-icon-s-cooperation', '借阅管理', 3, '2022-01-02 13:34:48', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (32, 0, '公告管理', 'sys:notice:index', 'notice', '/notice', '', '0', 'el-icon-tickets', '顶级菜单', 6, '2022-01-02 13:35:27', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (33, 32, '公告列表', 'sys:noticeList:index', 'noticeList', '/noticeList', '/notice/noticeList', '1', 'el-icon-chat-line-square', '公告管理', 1, '2022-01-02 13:36:14', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (34, 29, '借书', 'sys:bookBorrow:borrow', '', '', '', '2', '', '借书管理', 1, '2022-01-02 13:37:03', '2022-01-08 18:15:23');
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (37, 30, '新增', 'sys:bookReturn:add', '', '', '', '2', '', '还书管理', 1, '2022-01-02 13:38:01', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (38, 30, '编辑', 'sys:bookReturn:edit', '', '', '', '2', '', '还书管理', 2, '2022-01-02 13:38:17', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (39, 30, '删除', 'sys:bookReturn:delete', '', '', '', '2', '', '还书管理', 3, '2022-01-02 13:38:31', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (40, 31, '新增', 'sys:borrowLook:add', '', '', '', '2', '', '借阅查看', 1, '2022-01-02 13:38:58', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (41, 31, '编辑', 'sys:borrowLook:edit', '', '', '', '2', '', '借阅查看', 2, '2022-01-02 13:39:27', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (42, 31, '删除', 'sys:borrowLook:delete', '', '', '', '2', '', '借阅查看', 3, '2022-01-02 13:39:41', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (43, 33, '新增', 'sys:noticeList:add', '', '', '', '2', '', '公告列表', 1, '2022-01-02 13:40:07', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (44, 33, '编辑', 'sys:noticeList:edit', '', '', '', '2', '', '公告列表', 2, '2022-01-02 13:40:19', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (45, 33, '删除', 'sys:noticeList:delete', '', '', '', '2', '', '公告列表', 3, '2022-01-02 13:40:33', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (46, 33, '查看', 'sys:notice:look', '', '', '', '2', '', '公告列表', 4, '2022-01-08 18:13:03', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (47, 31, '借阅管理--查看', 'sys:borrowLook:look', '', '', '', '2', '', '借阅查看', 4, '2022-01-08 18:14:15', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (48, 15, '读者列表---审核', 'sys:reader:apply', '', '', '', '2', '', '读者列表', 4, '2022-01-08 20:35:38', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (49, 4, '菜单管理--编辑', 'sys:menu:edit', '', '', '', '2', '', '菜单管理', 2, '2022-01-08 20:39:15', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (50, 4, '菜单管理---删除', 'sys:menu:delete', '', '', '', '2', '', '菜单管理', 3, '2022-01-08 20:39:43', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (51, 31, '借阅查看---审核', 'sys:borrowLook:apply', '', '', '', '2', '', '借阅查看', 5, '2022-01-08 20:47:18', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (52, 31, '借阅查看---续期', 'sys:borrowLook:addTime', '', '', '', '2', '', '借阅查看', 5, '2022-01-09 12:40:12', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (53, 2, '用户管理----重置密码', 'sys:user:resetpassword', '', '', '', '2', '', '用户管理', 6, '2022-01-11 17:43:28', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (54, 15, '读者管理----重置密码功能', 'sys:reader:resetpassword', '', '', '', '2', '', '读者列表', 6, '2022-01-11 17:44:32', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (55, 0, '人脸管理', 'sys:face:index', 'face', '/face', '', '0', 'el-icon-camera', '顶级菜单', 7, '2022-03-19 21:54:17', null);
INSERT INTO LibManagement.menu (menu_id, parent_id, title, code, name, path, url, type, icon, parent_name, order_num, create_time, update_time) VALUES (56, 55, '人脸注册', 'sys:face:add', 'faceRegister', '/faceRegister', '/face/faceRegister', '1', 'el-icon-picture-outline', '人脸管理', 1, '2022-03-19 22:11:56', null);
create table notice
(
    notice_id      int auto_increment comment '主键'
        primary key,
    notice_title   varchar(128) null comment '标题',
    notice_content text         null comment '内容',
    create_time    datetime     null comment '创建时间'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.notice (notice_id, notice_title, notice_content, create_time) VALUES (1, '图书馆WIFI', '图书馆WiFi已接入校园WiFi！', '2022-01-02 11:40:48');
INSERT INTO LibManagement.notice (notice_id, notice_title, notice_content, create_time) VALUES (4, '放假通知', '学校于1月20日放假，图书馆将于1月18日停止开放；', '2022-01-10 14:04:54');
INSERT INTO LibManagement.notice (notice_id, notice_title, notice_content, create_time) VALUES (5, '开馆通知', '学校定于3月5日开学，图书馆将于3月6日正式开放!', '2022-01-10 14:12:09');
create table reader
(
    reader_id    int auto_increment comment '读者id'
        primary key,
    learn_num    varchar(36)  null comment '学生证号码',
    username     varchar(36)  null comment '姓名',
    id_card      varchar(20)  null comment '身份证号码',
    sex          varchar(2)   null comment '性别',
    phone        varchar(15)  null comment '电话',
    password     varchar(128) null comment '密码',
    type         varchar(2)   null comment '类型',
    check_status varchar(2)   null comment '审核状态 0：未审核  1：已审核',
    user_status  varchar(2)   null comment '用户状态 0:停用  1：启用',
    class_name   varchar(64)  null comment '班级'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.reader (reader_id, learn_num, username, id_card, sex, phone, password, type, check_status, user_status, class_name) VALUES (6, '张三', '2021001', '7894564633131', '0', '18687116223', '202cb962ac59075b964b07152d234b70', '0', '1', '1', '软件1班');
INSERT INTO LibManagement.reader (reader_id, learn_num, username, id_card, sex, phone, password, type, check_status, user_status, class_name) VALUES (7, '李明', '2021002', '8945613212313', '0', '18787171603', '202cb962ac59075b964b07152d234b70', '', '1', '1', '机械电子工程');
INSERT INTO LibManagement.reader (reader_id, learn_num, username, id_card, sex, phone, password, type, check_status, user_status, class_name) VALUES (8, '张明', '2021003', '864611321333132', '0', '18465461132', '202cb962ac59075b964b07152d234b70', '', '1', '1', '2021级软件1班');
INSERT INTO LibManagement.reader (reader_id, learn_num, username, id_card, sex, phone, password, type, check_status, user_status, class_name) VALUES (9, '测试1', '2021004', '456413132132233', '0', '18787171906', '202cb962ac59075b964b07152d234b70', '', '1', '1', '软件工程3班');

create table reader_role
(
    reader_role_id int auto_increment comment '主键'
        primary key,
    reader_id      int null comment '读者id',
    role_id        int null comment '角色id'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.reader_role (reader_role_id, reader_id, role_id) VALUES (5, 6, 5);
INSERT INTO LibManagement.reader_role (reader_role_id, reader_id, role_id) VALUES (6, 7, 5);
INSERT INTO LibManagement.reader_role (reader_role_id, reader_id, role_id) VALUES (7, 8, 5);
INSERT INTO LibManagement.reader_role (reader_role_id, reader_id, role_id) VALUES (8, 9, 5);
INSERT INTO LibManagement.reader_role (reader_role_id, reader_id, role_id) VALUES (10, 11, 5);
INSERT INTO LibManagement.reader_role (reader_role_id, reader_id, role_id) VALUES (11, null, 5);
INSERT INTO LibManagement.reader_role (reader_role_id, reader_id, role_id) VALUES (12, 12, 5);
create table role
(
    role_id     int auto_increment comment '角色id'
        primary key,
    role_name   varchar(36) null comment '角色名称',
    role_type   varchar(2)  null comment '角色类型 1：系统用户  2：读者',
    remark      varchar(64) null comment '备注',
    create_time datetime    null comment '创建时间',
    update_time datetime    null comment '更新时间'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.role (role_id, role_name, role_type, remark, create_time, update_time) VALUES (1, '超级管理员', '1', '超级管理员', '2021-12-06 13:50:28', null);
INSERT INTO LibManagement.role (role_id, role_name, role_type, remark, create_time, update_time) VALUES (5, '读者角色', '2', '读者角色只能有一个', '2022-01-04 16:19:57', null);
create table role_menu
(
    role_menu_id int auto_increment comment '主键'
        primary key,
    role_id      int null comment '角色id',
    menu_id      int null comment '菜单id'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (491, 1, 1);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (492, 1, 2);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (493, 1, 5);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (494, 1, 6);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (495, 1, 7);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (496, 1, 53);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (497, 1, 3);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (498, 1, 8);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (499, 1, 9);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (500, 1, 10);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (501, 1, 4);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (502, 1, 11);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (503, 1, 49);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (504, 1, 50);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (505, 1, 14);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (506, 1, 15);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (507, 1, 16);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (508, 1, 17);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (509, 1, 18);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (510, 1, 48);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (511, 1, 54);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (512, 1, 19);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (513, 1, 20);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (514, 1, 22);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (515, 1, 23);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (516, 1, 24);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (517, 1, 21);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (518, 1, 25);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (519, 1, 26);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (520, 1, 27);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (521, 1, 28);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (522, 1, 29);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (523, 1, 34);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (524, 1, 30);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (525, 1, 37);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (526, 1, 38);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (527, 1, 39);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (528, 1, 31);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (529, 1, 40);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (530, 1, 41);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (531, 1, 42);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (532, 1, 47);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (533, 1, 51);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (534, 1, 52);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (535, 1, 43);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (536, 1, 44);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (537, 1, 45);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (538, 1, 32);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (539, 1, 33);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (540, 5, 29);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (541, 5, 34);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (542, 5, 47);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (543, 5, 52);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (544, 5, 46);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (545, 5, 55);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (546, 5, 56);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (547, 5, 28);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (548, 5, 31);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (549, 5, 32);
INSERT INTO LibManagement.role_menu (role_menu_id, role_id, menu_id) VALUES (550, 5, 33);
create table user
(
    user_id                    int auto_increment comment '用户id'
        primary key,
    username                   varchar(36)  null comment '登录账户',
    password                   varchar(128) null comment '登录密码',
    phone                      varchar(13)  null comment '用户电话',
    email                      varchar(64)  null comment '邮箱',
    sex                        varchar(2)   null comment '0:男 1：女',
    is_admin                   varchar(2)   null comment '是否为超级管理员 1：是 0：否',
    is_account_non_expired     tinyint(2)   null comment '帐户是否过期(1 未过期，0已过期)',
    is_account_non_locked      tinyint(2)   null comment '帐户是否被锁定(1 未锁定，0已锁定)',
    is_credentials_non_expired tinyint(2)   null comment '密码是否过期(1 未过期，0已过期)',
    is_enabled                 tinyint(2)   null comment '帐户是否可用(1 可用，0 删除用户)',
    nick_name                  varchar(36)  null comment '姓名',
    create_time                datetime     null comment '创建时间',
    update_time                datetime     null comment '更新时间'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.user (user_id, username, password, phone, email, sex, is_admin, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, nick_name, create_time, update_time) VALUES (3, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '187652156545', '', '1', '1', 1, 1, 1, 1, '张三', '2021-11-30 15:05:04', '2022-03-22 22:28:18');
INSERT INTO LibManagement.user (user_id, username, password, phone, email, sex, is_admin, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, nick_name, create_time, update_time) VALUES (4, 'ls', 'e10adc3949ba59abbe56e057f20f883e', '18787171630', '', '1', '0', 1, 1, 1, 1, '李四', '2021-11-30 15:05:19', '2022-01-02 18:03:33');
INSERT INTO LibManagement.user (user_id, username, password, phone, email, sex, is_admin, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, nick_name, create_time, update_time) VALUES (5, 'cs1', 'e10adc3949ba59abbe56e057f20f883e', '18787171603', '', '0', '0', 1, 1, 1, 1, '测试', '2022-01-02 16:01:55', '2022-01-02 18:10:36');
INSERT INTO LibManagement.user (user_id, username, password, phone, email, sex, is_admin, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, nick_name, create_time, update_time) VALUES (6, 'cs111', 'e10adc3949ba59abbe56e057f20f883e', '18687116223', '', '0', '0', 1, 1, 1, 1, '测试11', '2022-01-02 18:03:59', null);
INSERT INTO LibManagement.user (user_id, username, password, phone, email, sex, is_admin, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, nick_name, create_time, update_time) VALUES (7, 'cs2', '202cb962ac59075b964b07152d234b70', '18787171906', '3501754007@qq.com', '0', '0', 1, 1, 1, 1, 'cs2', '2022-01-10 18:31:38', null);
INSERT INTO LibManagement.user (user_id, username, password, phone, email, sex, is_admin, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, nick_name, create_time, update_time) VALUES (8, 'cs3', 'f379eaf3c831b04de153469d1bec345e', '18687116223', '3501754007@22.com', '0', '0', 1, 1, 1, 1, 'cs3', '2022-01-10 19:15:29', null);
create table user_role
(
    user_role_id int auto_increment comment '主键'
        primary key,
    user_id      int null comment '用户id',
    role_id      int null comment '角色id'
)
    collate = utf8mb4_bin;

INSERT INTO LibManagement.user_role (user_role_id, user_id, role_id) VALUES (8, 4, 1);
INSERT INTO LibManagement.user_role (user_role_id, user_id, role_id) VALUES (9, 6, 3);
INSERT INTO LibManagement.user_role (user_role_id, user_id, role_id) VALUES (10, 5, 1);
INSERT INTO LibManagement.user_role (user_role_id, user_id, role_id) VALUES (14, 7, 5);
INSERT INTO LibManagement.user_role (user_role_id, user_id, role_id) VALUES (15, 8, 5);
INSERT INTO LibManagement.user_role (user_role_id, user_id, role_id) VALUES (16, 3, 1);
