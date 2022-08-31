def buildApp(){
    // sh "apk add make"
    sh "make build"
}

def auditApp(){
    sh "make build"
    sh "make audit"
}

def testApp(){
    sh "make test"
}

def secretScan(){
    sh "echo Scanning for credentials "
}

def imageBuild(){
    sh "sudo apt update"
    sh "sudo apt install apt-transport-https ca-certificates curl software-properties-common"
    sh "curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -"
    sh "sudo add-apt-repository 'deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable'"
    sh "apt-cache policy docker-ce"
    sh "sudo apt install docker-ce"
    sh "sudo systemctl status docker"
    sh "sudo usermod -aG docker ${USER}"
    sh "docker build -t mshallom/practicerepo:1.0 ."
    // sh "make docker_build imageName=mshallom/practicerepo:1.0"
}

def trivyScan(){
    sh "echo Trivy Scanning"
}

def pushImageToHub(){
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "make push imageName=mshallom/practicerepo:1.0"
    }
}

return this