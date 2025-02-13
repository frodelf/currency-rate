To work, you need to run a docker container that simulates the work of the API to get the current exchange rate and the current exchange rate of crypto currencies.

docker build -t currencies-mocks https://github.com/illenko/currencies-mocks.git
docker run -d -p 8080:8080 currencies-mocks
