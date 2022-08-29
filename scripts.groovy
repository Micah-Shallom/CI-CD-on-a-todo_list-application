def buildApp(){
    sh "ls"
}

def auditApp(){
    sh "make build"
    sh "make audit"
}

return this