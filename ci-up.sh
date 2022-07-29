#sh
docker-compose -p jenkins-env --file docker-compose-jenkins.yml up -d --build
sleep 2
docker rm jenkins-env-jenkins-bucket-creation-1
echo "Jenkins environment started..."

