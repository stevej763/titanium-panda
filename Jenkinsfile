#!groovy

String branchName = env.BRANCH_NAME
String buildId = env.BUILD_ID
String jenkinsUrl = env.JENKINS_URL

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
                    [credentialsId: 'jenkins-ci',
                    url: 'https://github.com/stevej763/titanium-panda.git']
                ]
            ])
        commitShaOfBranch = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()

    }

    stage('Maven Build') {
        echo "Building ${branchName}...."
        withEnv(["PATH+MAVEN=${tool 'Maven'}/bin"]) {
                'mvn clean package -DskipTests=true'
            }
    }

    stage('Maven Test') {
        withEnv(["PATH+MAVEN=${tool 'Maven'}/bin"]) {
                    sh 'mvn test'
                }
        always {
            echo 'Testing ${branchName}....'
        }
       if (currentBuild.result == null || currentBuild.result == 'SUCCESS') {
           echo "Passed tests for build #${buildId} on branch: ${BRANCH_NAME}"
        }
        if (currentBuild.result == "FAILURE") {
            echo "Failed tests on ${BRANCH_NAME}"
        }
    }
    stage('Merge on pass') {
        if ((currentBuild.result == null || currentBuild.result == 'SUCCESS') && BRANCH_NAME != 'main') {

            withCredentials([usernamePassword(credentialsId: 'jenkins-ci', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                sh('git config --global user.email "${GIT_USERNAME}@ci.com"')
                sh('git config --global user.name "${GIT_USERNAME}"')
                sh('git fetch https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/stevej763/titanium-panda.git')
                sh('git checkout main')
                echo(commitShaOfBranch)
                sh "git merge ${commitShaOfBranch}"
                sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/stevej763/titanium-panda.git')
            }
        }
    }
    stage('Deploy') {
        echo 'Reached deploy stage'
    }
}
