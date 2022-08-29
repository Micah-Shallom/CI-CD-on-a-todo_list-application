def gv

pipeline{
   agent {
        docker {
            image 'node:16.13.1-alpine'
            args '-u root --privileged'
            args '-v $Jenkins_Home:/root/.m2'
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