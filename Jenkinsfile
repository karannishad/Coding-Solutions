pipeline {
    agent any

    stages {

        stage('build') {
            steps {
                sh 'echo "Welcome to git"'
                sh 'java -version'

            }
        }
        stage('Sonarqube') {
            environment {
                scannerHome = tool 'sonar-scanner'
            }
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

    }
}