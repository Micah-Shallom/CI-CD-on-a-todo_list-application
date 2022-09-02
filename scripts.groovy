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

// def sonarScan(){
//     def scannerHome = tool 'sonarscan';
//     withsonarQubeEnv(installationName:"sq1"){
//         sh "${tool("sonarscan")}/bin/sonar-scanner -Dsonar.projectKey=reactapp -Dsonar.projectName=reactapp"
//     }
//     def qualitygate = waitForQualityGate()
//     sleep(10)
//     if (qualitygate.status != "OK") {
//     waitForQualityGate abortPipeline: true
//     }
// }

def imageBuild(String IMAGE_NAME){
    sh "make docker_build imageName=$IMAGE_NAME"
}

// def trivyScan(String IMAGE_NAME){
//     // Install trivy
//     // sh 'curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sudo sh -s -- -b /usr/local/bin v0.18.3'
//     sh 'wget https://github.com/aquasecurity/trivy/releases/download/v0.18.3/trivy_0.18.3_Linux-64bit.deb'
//     sh 'sudo dpkg -i trivy_0.18.3_Linux-64bit.deb'

//     // Scan again and fail on CRITICAL vulns
//     sh "trivy image $IMAGE_NAME"

// }

def pushImageToHub(){
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "make push imageName=mshallom/practicerepo:1.0"
    }
}

return this