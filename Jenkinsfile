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
            environment {
                DOCKER_HUB = credentials('dockerhub-cred')
            }
            steps {
                bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
                bat "docker push atharvahiwase7/flightreservationjenkins:latest"
                bat "docker tag atharvahiwase7/flightreservationjenkins:latest atharvahiwase7/flightreservationjenkins:${env.BUILD_NUMBER}"
                bat "docker push atharvahiwase7/flightreservationjenkins:${env.BUILD_NUMBER}"
            }
        }
    }

    post {
        always {
            bat "docker logout"
        }
    }
}