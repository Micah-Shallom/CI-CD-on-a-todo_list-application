def buildApp(){
    sh "apk add make"
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
    sh "make docker_build imageName=mshallom/practicerepo:1.0"
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