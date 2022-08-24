build:
	echo $(foo)
	npm install

audit:
	npm audit fix --audit-level=critical --force
	npm audit --audit-level=critical