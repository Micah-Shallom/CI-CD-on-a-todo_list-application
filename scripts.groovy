def buildApp(){
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

def sonarScan(){
    def scannerHome = tool 'sonarscan';
    withsonarQubeEnv(installationName:"sq1"){
        sh "${tool("sonarscan")}/bin/sonar-scanner -Dsonar.projectKey=reactapp -Dsonar.projectName=reactapp"
    }
    def qualitygate = waitForQualityGate()
    sleep(10)
    if (qualitygate.status != "OK") {
    waitForQualityGate abortPipeline: true
    }
}

def sonarAnalysis(){
    def scannerHome = tool 'SonarQubeScanner-4.7.0';
    withSonarQubeEnv('sq1') {
        sh "${tool("SonarQubeScanner-4.7.0")}/bin/sonar-scanner -Dsonar.projectKey=devops-accelerate -Dsonar.projectName=devops-accelerate"
    }
}
def qualityGate(){
    def qualitygate = waitForQualityGate()
    sleep(10)
    if (qualitygate.status != "OK") {
        waitForQualityGate abortPipeline: true
    }
}

def imageBuild(String IMAGE_NAME){
    sh "make docker_build imageName=$IMAGE_NAME"
}

def trivyScan(String IMAGE_NAME){
    // Install trivy
    sh 'wget https://github.com/aquasecurity/trivy/releases/download/v0.18.3/trivy_0.18.3_Linux-64bit.deb'
    sh 'sudo dpkg -i trivy_0.18.3_Linux-64bit.deb'

    // Scan again and fail on CRITICAL vulns
    sh "trivy image $IMAGE_NAME"

}

def pushImageToHub(){
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "make push imageName=mshallom/practicerepo:1.0"
    }
}

def deployInfrastructure(){
    sh "make init"
    sh "make plan"
    sh "make apply"

    instance_ip = sh(returnStdout: true, script: "terraform output ec2_public_ip").trim()
    instance_key_name = sh(returnStdout: true, script: "terraform output key_name").trim()
}

def deployScript(){
    def shellCMD = "bash ./command.sh"
    def ec2Instance = "ec2-user@${instance_ip}"
    sshagent(["${instance_key_name}"]) {
        sh "scp command.sh ${ec2Instance}:/home/ec2-user"
        sh "scp docker-compose.yaml ${ec2Instance}:/home/ec2-user"
        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCMD}"
    }
}

return this