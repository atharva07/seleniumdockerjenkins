FROM bellsoft/liberica-openjdk-alpine:17-cds-x86_64

# install curl and jq
RUN apk add curl jq

# workspace
WORKDIR /home/flightreservation

# we can add the required file to run the test
ADD target/docker-resources ./
ADD runner.sh      runner.sh

# start the runner.sh
# once the changes are made in the automation script
# 1. create a package by hitting the command - mvn clean package -DskipTests
# 2. Then build  the image using the command - docker build -t=[username]/[imagename]
# 3. Once the container is build and ready. hit the command in the location where docker yaml file is stored. - docker-compose up
# 4. check the results in the output file which we created using volume mapping method.

# Test results are inside the container
# We can get the test results to the machine by doing volume mapping

# Hub takes time to connect. So script fails immediately.
# We created a "runner" script which checks the grid "readiness". Then run the tests!

# Run the command to initiate the tests
#ENTRYPOINT java -cp "libs/*" -Dselenium.grid.enabled=true -Dselenium.grid.hubHost=${HUB_HOST} -Dbrowser=${BROWSER} org.testng.TestNG -threadcount ${THREAD_COUNT} test-suites/${TEST_SUITE}

# start the runner.sh
ENTRYPOINT sh runner.sh