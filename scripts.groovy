
def buildApp(){
    sh "yum install npm -y"
    sh "npm i"
}

return this