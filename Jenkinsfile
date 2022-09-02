def gv

pipeline{
    agent any
    environment{
        IMAGE_NAME= "mshallom/practicerepo:1.0"
    }
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

        // stage("building application"){
        //     steps{
        //         script{
        //             echo "====++++Building Application++++===="
        //             gv.buildApp()
        //         }
        //     }
        // }

        // stage("auditing application"){
        //     steps{
        //         script{
        //             echo "====++++Auditing Application++++===="
        //             gv.auditApp()
        //         }
        //     }
        // }

        // stage("testing application"){
        //     steps{
        //         script{
        //             echo "====++++Testing Application++++===="
        //             gv.testApp()
        //         }
        //     }
        // }

        // stage("scan_for_secrets"){
        //     steps{
        //         script{
        //             echo "====++++Scan App For Secrets++++===="
        //             gv.secretScan()
        //         }
        //     }
        // }
        // stage("static_code_analysis"){
        //     steps{
        //         script{
        //             echo "====++++SonarQube Scan++++===="
        //             gv.sonarScan()
        //         }
        //     }
        // }
        stage('SonarQube analysis') {
            steps {
                script {
                    def scannerHome = tool 'SonarQubeScanner-4.7.0';
                    withSonarQubeEnv('sq1') {
                        sh "${tool("SonarQubeScanner-4.7.0")}/bin/sonar-scanner -Dsonar.projectKey=devops-accelerate -Dsonar.projectName=devops-accelerate"
                    }
                }
            }
    }
        stage("Quality gate") {
            steps {
                script {
                    def qualitygate = waitForQualityGate()
                    sleep(10)
                        if (qualitygate.status != "OK") {
                            waitForQualityGate abortPipeline: true
                        }
                }
            }
        }

        stage("Building and Testing Image"){
            steps{
                script{
                    echo "====++++Building Image++++===="
                    gv.imageBuild(env.IMAGE_NAME)
                    echo "====++++Trivy Scan Image++++===="
                    // gv.trivyScan(env.IMAGE_NAME)
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