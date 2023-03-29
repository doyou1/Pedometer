# in project
  # gradle build
  ./gradlew build -x test (-x test: 테스트 실행 X)

  # docker image build
  docker build --build-arg DEPENDENCY=build/dependency -t {dockerhub id}/{dockerhub repo} .

  # docker image push
  docker push {dockerhub id}/{dockerhub repo}

# in aws ec2
  # (if you need) docker install
  sudo yum install docker

  # (if you need) docker execute
  sudo systemctl start docker

  # docker image pull in docker hub
  sudo docker pull {dockerhub id}/{dockerhub repo}

  # deploy spring boot app for docker image
  sudo docker run -p 8080:8080 {dockerhub id}/{dockerhub repo}