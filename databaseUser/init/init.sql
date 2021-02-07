create database if not exists user;
grant all privileges on *.* to 'tomcatuser'@'%';
flush privileges;