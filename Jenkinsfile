def gv

pipeline{
//    agent {
//         docker {
//             image 'node:16.13.1-alpine'
//             args '-u root --privileged'
//         }
//     }
    agent none
    stages{
        stage("init"){
            steps{
                echo "========executing app initialization========"
                script{
                    checkout scm
                    gv =  load "scripts.groovy"
                }
            }
        }

        stage("building application"){
            agent {
                docker {
                    image 'node:16.13.1-alpine'
            }
        }
            steps{
                script{
                    echo "====++++Building Application++++===="
                    gv.buildApp()
                }
            }
        }
        stage("auditing application"){
            agent {
                docker {
                    image 'node:16.13.1-alpine'
            }
        }
            steps{
                script{
                    echo "====++++Auditing Application++++===="
                    gv.auditApp()
                }
            }
        }
        stage("testing application"){
            agent {
                docker {
                    image 'node:16.13.1-alpine'
            }
        }
            steps{
                script{
                    echo "====++++Testing Application++++===="
                    gv.testApp()
                }
            }
        }
        stage("scan_for_secrets"){
            steps{
                script{
                    echo "====++++Scan App For Secrets++++===="
                    gv.secretScan()
                }
            }
        }
        stage("Building and Testing Image"){
            
            steps{
                script{
                    echo "====++++Building Image++++===="
                    gv.imageBuild()
                    echo "====++++Trivy Scan Image++++===="
                    gv.trivyScan()
                    echo "====++++Push Image++++===="
                    gv.pushImageToHub()
                }
            }
        }
    }

    post{
        always{
            echo "========always========"
        }
        success{
            echo "========pipeline executed successfully ========"
        }
        failure{
            echo "========pipeline execution failed========"
        }
    }
}