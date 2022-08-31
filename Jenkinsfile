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

        stage("Building and Testing Image"){
            steps{
                script{
                    echo "====++++Building Image++++===="
                    gv.imageBuild(env.IMAGE_NAME)
                    echo "====++++Trivy Scan Image++++===="
                    gv.trivyScan(env.IMAGE_NAME)
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