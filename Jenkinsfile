pipeline {
    agent { docker { image 'gradle:latest' } }

    stages {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/karannishad/Coding-Solutions.git']]])
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}