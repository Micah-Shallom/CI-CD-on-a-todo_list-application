FROM node:16-alpine

EXPOSE 5000

ENV MONGO_DB_USERNAME=admin \
    MONGO_DB_PWD=password

RUN mkdir -p /home/app

COPY . /home/app

WORKDIR /home/app

CMD ["npm" , "install"]

# CMD ["npm" , "run", "start"]
