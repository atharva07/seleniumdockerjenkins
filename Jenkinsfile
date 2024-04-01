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
                bat "docker build -t=atharvahiwase7/flightreservation:latest ."
            }
        }

        stage('Push Image') {
            environment {
                DOCKER_HUB = credentials('dockerhub-cred')
            }
            steps {
                bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
                bat "docker push atharvahiwase7/flightreservation"
                //bat "docker tag atharvahiwase7/flightreservation:latest atharvahiwase7/flightreservation:${env.BUILD_NUMBER}"
                //bat "docker push atharvahiwase7/flightreservation:${env.BUILD_NUMBER}"
            }
        }
    }

    post {
        always {
            bat "docker logout"
        }
    }
}