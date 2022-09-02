variable env_prefix {}
variable vpc_cidr_block {}
variable subnet_cidr_block {}
variable avail_zone{}
variable public_key_location{}
variable access_key{}
variable secret_key{}

provider "aws" {
    region = "us-east-1"
    access_key= var.access_key
    secret_key= var.secret_key

}
resource "aws_vpc" "app-vpc" {
    cidr_block       = var.vpc_cidr_block
    instance_tenancy = "default"

    tags = {
        Name = "${var.env_prefix}-vpc"
    }
}

resource "aws_subnet" "app-subnet"{
    vpc_id     = aws_vpc.app-vpc.id
    cidr_block = var.subnet_cidr_block
    availability_zone = var.avail_zone

    tags = {
        Name = "${var.env_prefix}-subnet"
    }
}

resource "aws_route_table" "app-rtb" {
  vpc_id = aws_vpc.app-vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.app-gw.id
  }

  tags = {
    Name = "${var.env_prefix}-rtb"
  }
}

resource "aws_internet_gateway" "app-gw" {
  vpc_id = aws_vpc.app-vpc.id

  tags = {
    Name = "${var.env_prefix}-igw"
  }
}

resource "aws_route_table_association" "app-rtba" {
  subnet_id      = aws_subnet.app-subnet.id
  route_table_id = aws_route_table.app-rtb.id
}

resource "aws_security_group" "myapp-sg" {
    name="myapp-sg"
    vpc_id = aws_vpc.app-vpc.id

    ingress {
        from_port = 22
        to_port = 22
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    ingress {
        from_port = 8080
        to_port = 8080
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    ingress {
        from_port = 5000
        to_port = 5000
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    egress{
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
        Name = "${var.env_prefix}-sg"
    }
} 

data "aws_ami" "amazon-ami" {
  most_recent = true
    owners = ["amazon"]
    filter{
        name="name"
        values=["amzn2-ami-hvm-*-x86_64-gp2"]
    }
    filter{
        name="virtualization-type"
        values=["hvm"]
    }
}

resource "aws_key_pair" "ssh-key" {
    key_name = "server-key"
    public_key = file(var.public_key_location)
}


resource "aws_instance" "amazon-server" {
    ami = data.aws_ami.amazon-ami.id
    instance_type = "t2.micro"

    subnet_id = aws_subnet.app-subnet.id
    vpc_security_group_ids = [aws_security_group.myapp-sg.id]
    availability_zone = var.avail_zone

    associate_public_ip_address = true
    key_name = aws_key_pair.ssh-key.key_name

    user_data = file("command.sh")

    tags = {
    Name = "${var.env_prefix}-server"
    }
}

output "aws_ami_id" {
    value = data.aws_ami.amazon-ami.id
}
output "ec2_public_ip" {
    value = aws_instance.amazon-server.public_ip
}
output "key_name" {
    value = aws_key_pair.ssh-key.key_name
}