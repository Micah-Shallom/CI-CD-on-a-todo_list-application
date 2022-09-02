build:
	npm install

audit:
	npm audit fix --audit-level=critical --force
	npm audit --audit-level=critical

test:
	npm install --save -dev mocha chai
	npm run test

docker_build:
	docker build -t $(imageName) .

push:
	docker push $(imageName)

init:
	terraform init

plan:
	terraform plan

apply:
	terraform apply -auto-approve

compose_up:
	docker-compose -f docker-compose.yaml up -d 

compose_down:
	docker-compose -f docker-compose.yaml down