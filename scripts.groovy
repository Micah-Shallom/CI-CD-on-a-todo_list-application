def buildApp(){
    sh "apk add make"
    sh "make build"
}

def auditApp(){
    sh "make build"
    sh "make audit"
}

return this