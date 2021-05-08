pipeline {
    agent { docker { image 'gradle:latest' } }

    stages {

        stage('build') {
            steps {
                sh 'echo "Welcome to git"'
                sh 'java --version'
                sh 'mvn --version'
            }
        }
    }
}