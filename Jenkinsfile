pipeline {
    agent any
    stages {
        stage ('Jenkins Gateway') {
            steps {
                echo 'Gateway'
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}