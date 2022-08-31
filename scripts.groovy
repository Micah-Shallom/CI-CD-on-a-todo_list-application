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

def imageBuild(){
    sh "make docker_build imageName=mshallom/practicerepo:1.0"
}

def trivyScan(String IMAGE_NAME){
    // Install trivy
    sh 'curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin v0.18.3'
    sh 'curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/html.tpl > html.tpl'

    // Scan all vuln levels
    sh 'mkdir -p reports'
    sh 'trivy filesystem --ignore-unfixed --vuln-type os,library --format template --template "@html.tpl" -o reports/nodjs-scan.html ./nodejs'
    publishHTML target : [
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'reports',
        reportFiles: 'nodjs-scan.html',
        reportName: 'Trivy Scan',
        reportTitles: 'Trivy Scan'
    ]

    // Scan again and fail on CRITICAL vulns
    sh 'trivy filesystem --ignore-unfixed --vuln-type os,library --exit-code 1 --severity CRITICAL ./nodejs'
    sh "trivy image --severity HIGH,CRITICAL ${IMAGE_NAME}"

}

def pushImageToHub(){
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "make push imageName=mshallom/practicerepo:1.0"
    }
}

return this