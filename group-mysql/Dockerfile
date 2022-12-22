FROM mysql:8.0
USER root
ENV MYSQL_ROOT_PASSWORD 1234
ADD group_users.sql /docker-entrypoint-initdb.d
EXPOSE 3307