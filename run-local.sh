#sh

echo "Compliling app..."
mvn clean package -q -DskipTests=true -Dspring.profiles.active=docker
echo "App compiled"
docker-compose up -d --build --remove-orphans
echo "Dev environment started..."
