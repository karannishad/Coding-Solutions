pipeline {
    agent { docker { image 'gradle:latest' } }

    stages {
        stage('checkout'){
            git 'https://github.com/karannishad/Coding-Solutions.git'
        }
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}