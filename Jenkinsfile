pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh buildArtifacts()
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploy..'
                sh deploy()
            }
        }
    }
}

def buildArtifacts() {
    return """#!/usr/bin/env bash
        chmod +x ./mvnw && ./mvnw clean compile jib:buildTar -DskipTest=true
    """
}

def deploy() {
    return """#!/usr/bin/env bash
        scp LibManagement_web/target/jib-image.tar root@192.168.1.1:jib-image.tar
        ssh root@192.168.1.1 docker load --input jib-image.tar
    """
}

