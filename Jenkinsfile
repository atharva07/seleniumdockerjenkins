pipeline {
    agent any

    stages {
        stage('Build JAR') {
            steps {
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build Image') {
            steps {
                bat "docker build -t=atharvahiwase7/flightreservationjenkins:latest ."
            }
        }

        stage('Push Image') {
            steps {
                bat "docker push atharvahiwase7/flightreservationjenkins:latest"
            }
        }
    }
}