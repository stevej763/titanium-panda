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
        stage('merge') {
            steps {

                    withCredentials([usernamePassword(credentialsId: 'jenkins-ci', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                        sh('git config --global user.email "${GIT_USERNAME}@ci.com"')
                        sh('git config --global user.name "${GIT_USERNAME}"')
                        sh('git fetch https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/stevej763/titanium-panda.git')
                        sh('git checkout main')
                        sh('git merge ${GIT_COMMIT}')
                        sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/stevej763/titanium-panda.git')
                    }
                }
            }
    }
}