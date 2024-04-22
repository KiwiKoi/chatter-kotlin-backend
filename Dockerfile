FROM ubuntu:latest
LABEL authors="daniel"

ENTRYPOINT ["top", "-b"]

FROM postgres:latest

# Set environment variables
ENV POSTGRES_USER=daniel
ENV POSTGRES_PASSWORD=daniel
ENV POSTGRES_DB=chatter_db

# Copy initialization scripts
COPY init.sql /docker-entrypoint-initdb.d/

# Expose PostgreSQL port
EXPOSE 5432