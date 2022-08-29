def gv

pipeline{
    agent {
        docker { image 'node:16.13.1-alpine' }
        args '-v $HOME/.m2:/root/.m2'
    }
    stages{

        stage("init"){
            steps{
                echo "========executing app initialization========"
                script{
                    gv =  load "scripts.groovy"
                }
            }
            post{
                success{
                    echo "========A executed successfully========"
                }
                failure{
                    echo "========A execution failed========"
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