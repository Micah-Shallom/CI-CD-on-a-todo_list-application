// def buildApp(){
//     sh "make build"
// }

// def auditApp(){
//     sh "make build"
//     sh "make audit"
// }

// def testApp(){
//     sh "make test"
// }

// def secretScan(){
//     sh "echo Scanning for credentials "
// }

def imageBuild(String IMAGE_NAME){
    sh "make docker_build imageName=$IMAGE_NAME"
}

def trivyScan(String IMAGE_NAME){
    // Install trivy
    sh 'curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/master/contrib/install.sh | sudo sh -S -- -b /usr/local/bin'

    // Scan again and fail on CRITICAL vulns
    sh "trivy image --severity HIGH,CRITICAL $IMAGE_NAME"

}

def pushImageToHub(){
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "make push imageName=mshallom/practicerepo:1.0"
    }
}

return this