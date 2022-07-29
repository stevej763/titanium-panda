#!groovy

String branchName = env.BRANCH_NAME
String buildId = env.BUILD_ID
String jenkinsUrl = env.JENKINS_URL
String main_branch = 'main'

def setCreds() {
    sh('git config --global user.email "${GIT_USERNAME}@ci.com"')
    sh('git config --global user.name "${GIT_USERNAME}"')
}

node {

    stage('Pipeline Setup') {
        setCreds()
        echo "Running build #${buildId} on ${jenkinsUrl}"
        checkout([
                $class: 'GitSCM',
                branches: [[name: branchName]],
                extensions: [],
                userRemoteConfigs: [
                    [credentialsId: 'jenkins-github',
                    url: 'https://github.com/stevej763/titanium-panda.git']
                ]
            ])
        commitShaOfBranch = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()

    }

    stage('Maven Build') {
        echo "Building ${branchName}...."
        withEnv(["PATH+MAVEN=${tool 'Maven'}/bin"]) {
                sh 'mvn clean package -DskipTests=true -Dspring.profiles.active=jenkins'
            }
    }

    stage('Maven Test') {
        mongoContainer = sh 'docker run -d mongodb:latest'
        withEnv(["PATH+MAVEN=${tool 'Maven'}/bin"]) {
                    sh 'mvn test -Dspring.profiles.active=jenkins'
                }
        echo 'Testing ${branchName}....'
        sh 'docker stop${mongoContainer}'

    }
    stage('Merge on pass') {
        if ((currentBuild.result == null || currentBuild.result == 'SUCCESS') && BRANCH_NAME != 'main') {

            withCredentials([usernamePassword(credentialsId: 'jenkins-ci', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                sh('git config --global user.email "${GIT_USERNAME}@ci.com"')
                sh('git config --global user.name "${GIT_USERNAME}"')
                sh('git fetch https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/stevej763/titanium-panda.git')
                sh('git checkout main')
                sh "git merge ${commitShaOfBranch}"
                sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/stevej763/titanium-panda.git')
            }
        }
        if (BRANCH_NAME == main_branch) {
            echo("On branch ${main_branch}. No merge necessary.")
        }
    }
    stage('Build Image') {
            local_image='titanium-panda:' + BUILD_ID
            echo "${local_image}"
            sh 'docker build -t titanium-panda:"${BUILD_ID}" .'
         }

    stage('Push image') {
        if (BRANCH_NAME == main_branch) {
            withCredentials([usernamePassword(credentialsId: 'docker-hub', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                        sh 'docker login -u "${USERNAME}" -p "${PASSWORD}"'
                        sh 'docker tag titanium-panda:0.0."${BUILD_ID}" "${USERNAME}"/photo-portfolio:titanium-panda-"${BUILD_ID}"'
                        sh 'docker push "${USERNAME}"/photo-portfolio:titanium-panda-"${BUILD_ID}"'
                    }
        }
    }
}
