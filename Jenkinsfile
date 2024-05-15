pipeline {
    agent any
    environment {
        K8S_PORT = 64590
    }
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
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Image') {
            steps {
                script {
                    account = docker.build("alanmath/gateway:${env.BUILD_ID}", "-f Dockerfile .")
                }
            }
        }
        stage('Push Image'){
            steps{
                script {
                    docker.withRegistry('https://registry.hub.docker.com', '594871ef-08ca-47da-8365-6dd98ca976e6') {
                        account.push("${env.BUILD_ID}")
                        account.push("latest")
                    }
                }
            }
        }
        stage('Deploy on k8s') {
            steps {
                withCredentials([string(credentialsId: 'my_kubernetes', variable: 'api_token')]) {
                    sh "kubectl --token $api_token --server https://host.docker.internal:${env.K8S_PORT} --insecure-skip-tls-verify=true apply -f ./k8s/deployment.yaml --validate=false"
                    sh "kubectl --token $api_token --server https://host.docker.internal:${env.K8S_PORT} --insecure-skip-tls-verify=true apply -f ./k8s/service.yaml --validate=false"
                }
            }
        }
    }
}
