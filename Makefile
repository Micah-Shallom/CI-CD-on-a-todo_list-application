build:
	npm install --save -dev mocha chai
	npm install

audit:
	npm audit fix --audit-level=critical --force
	npm audit --audit-level=critical

docker_build:
	docker build -t $(imageName) .

push:
	docker push $(imageName)

compose_up:
	docker-compose -f docker-compose.yaml up -d 

compose_down:
	docker-compose -f docker-compose.yaml down