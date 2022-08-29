def gv

pipeline{
   agent {
        docker {
            image 'cimg/node:15.0.1'
            args '-v $HOME/.m2:/root/.m2'
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