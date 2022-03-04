pipeline {
    agent any

    tools {
            maven "Maven"
        }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Test') {
                    steps {
                        sh 'mvn test'
                    }
                    post {
                        always {
                            junit 'target/surefire-reports/*.xml'
                        }
                    }
                }
    }
}