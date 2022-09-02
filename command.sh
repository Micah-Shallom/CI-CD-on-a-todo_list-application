#!/usr/bin/env groovy


sudo yum update -y && sudo yum install -y docker
sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo systemctl start docker
sudo usermod -aG docker ec2-user
docker-compose version
make compose_up
# docker-compose -f docker-compose.yaml up --detach 
echo "------------SUCCESS-------------------"