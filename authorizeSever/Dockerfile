FROM centos:7
MAINTAINER Jun-Young Jeong <abcdefssk1@gmail.com>

# Install basic packages
ENV container docker

RUN (cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == \
systemd-tmpfiles-setup.service ] || rm -f $i; done); \
rm -f /lib/systemd/system/multi-user.target.wants/*;\
rm -f /etc/systemd/system/*.wants/*;\
rm -f /lib/systemd/system/local-fs.target.wants/*; \
rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
rm -f /lib/systemd/system/basic.target.wants/*;\
rm -f /lib/systemd/system/anaconda.target.wants/*;\
yum -y update

RUN yum install -y java-1.8.0-openjdk-devel.x86_64

WORKDIR /var/server/

#Copy runnable jar
COPY /build/libs/authorizeSever-0.0.1-SNAPSHOT.jar authorizeSever.jar

EXPOSE 8080

#run runnable jar
CMD nohup java -jar /var/server/authorizeSever.jar

