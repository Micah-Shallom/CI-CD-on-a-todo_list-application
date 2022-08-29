circle-config: SHELL:=/bin/bash
circle-config:
	circleci -h >/dev/null 2>&1 || { echo >&2 "Install the circleci cli with brew install"; }
	cd .circleci && circleci config pack src > config.yml
	circleci config validate

build:
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