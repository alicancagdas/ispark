# MongoDB imajını temel al
FROM mongo:latest

# MongoDB shell (mongo) paketini yükle
RUN apt-get update && \
    apt-get install -y gnupg wget && \
    wget -qO - https://www.mongodb.org/static/pgp/server-7.0.asc | apt-key add - && \
    echo "deb [ arch=amd64 ] https://repo.mongodb.org/apt/debian bullseye mongodb-org/7.0" | tee /etc/apt/sources.list.d/mongodb-org-7.0.list && \
    apt-get update && \
    apt-get install -y mongodb-org-shell

# Varsayılan komut
CMD ["mongod"]
