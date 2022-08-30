def gv

pipeline{
   agent {
        docker {
            // image 'node:16.13.1-alpine'
            image 'cimg/node:15.0.1'
            args '-u root --privileged'
        }
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