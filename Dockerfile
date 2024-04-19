FROM postgres:latest
COPY /src/main/resources/scripts.sql /docker-entrypoint-initdb.d/
ENV POSTGRES_DB=teste \
    POSTGRES_USER=user \
    POSTGRES_PASSWORD=123
