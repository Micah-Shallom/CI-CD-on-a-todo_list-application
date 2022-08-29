def gv

pipeline{
   agent {
        node('docker') {
            docker.image('node:16.13.1-alpine').inside(' -u 0') {
                sh 'apk add curl'
  }
}
    }
    stages{

        stage("init"){
            steps{
                echo "========executing app initialization========"
                script{
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