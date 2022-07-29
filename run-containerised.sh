#sh

echo "Compliling app..."
mvn clean package -q -DskipTests=true -Dspring.profiles.active=docker
echo "App compiled"
docker-compose -p photos-api up -d --build --remove-orphans
sleep 2
docker rm photos-api-bucket-creation-1
echo "Dev environment started..."
