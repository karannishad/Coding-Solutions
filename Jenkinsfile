pipeline {
    agent none

    stages {
        stage('build') {
            agent {
                docker { image 'maven:3.3.3' }
            }
            steps {
                sh 'echo "Welcome to git"'
                sh 'java -version'

            }
        }
    }
}