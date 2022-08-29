def buildApp(){
    sh "make build"
}

def auditApp(){
    sh "make build"
    sh "make audit"
}

return this