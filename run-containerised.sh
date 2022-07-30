#sh

echo "Compliling app..."
mvn clean package -q -DskipTests=true -Dspring.profiles.active=docker
echo "App compiled"
export ACCESS_KEY=AKIA55PL6RVEWUHWYYM2
export SECRET_KEY=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
docker-compose  -p photos-api up -d --build --remove-orphans
sleep 2
docker rm photos-api-bucket-creation-1
echo "Dev environment started..."
