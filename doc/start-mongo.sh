$ docker run -it --network some-network --rm mongo mongo --host some-mongo test

$ docker run -it --network some-network --rm mongo mongo --host some-mongo test

docker run -p 8081:8081 -p 27017:27017 --name todo-mongo -d mongo:3.6

docker stop todo-mongo
docker rm todo-mongo