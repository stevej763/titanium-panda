pipeline {
    agent any

    tools {
            maven "Maven"
        }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn clean package -DskipTests=true -Dspring.profiles.active=jenkins'
            }
        }
        stage('Test') {
                    steps {
                        sh 'mvn test -Dspring.profiles.active=jenkins'
                    }
                    post {
                        always {
                            junit 'target/surefire-reports/*.xml'
                        }
                    }
                }
    }
}