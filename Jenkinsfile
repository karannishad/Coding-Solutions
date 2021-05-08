pipeline {
    agent { docker { image 'gradle:latest' } }

    stages {

        stage('build') {
            steps {
                sh 'java --version'
                sh 'mvn --version'
            }
        }
    }
}