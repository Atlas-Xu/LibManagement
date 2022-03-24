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
        chmod +x ./mvnw && ./mvnw clean package -DskipTest=true
    """
}

def deploy() {
    return """#!/usr/bin/env bash
        scp LibManagement_web/target/LibManagement_web-1.0-SNAPSHOT.jar root@192.168.1.1:lib-management/web.jar
        ssh  root@192.168.1.1 docker restart lib-management
    """
}

