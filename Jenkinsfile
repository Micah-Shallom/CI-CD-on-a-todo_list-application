def gv

pipeline{
    agent any
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
            steps{
                script{
                    echo "====++++Building Application++++===="
                    gv.buildApp()
                }
            }
        }
        stage("auditing application"){
            steps{
                script{
                    echo "====++++Auditing Application++++===="
                    gv.auditApp()
                }
            }
        }
        stage("testing application"){
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
            agent {
                docker {
                    image 'docker:latest'
                    args '-u root --privileged'
                }
    }
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