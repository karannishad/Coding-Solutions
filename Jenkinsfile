pipeline {
    agent { docker { image 'gradle:latest' } }

    stages {
        stage('checkout'){
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/karannishad/Coding-Solutions.git']]])
        }
        stage('build') {
            steps {
                sh 'java --version'
                sh 'mvn --version'
            }
        }
    }
}