# Vehicle tracking

Vehicle tracker is a service to track and detect vehivles in the map. 
Inludes next API endpoints:

  - GET: 127.0.0.1:8080/track?left_lon=-0.15&left_lat=-0.1&right_lon=15.88&right_lat=24.55557
  - PUT: 127.0.0.1:8080/track/v1

Second API requires body in the next format: 
```json
{
	"vehicleName": "v1", 
	"latitude": "15.294746",
	"longitude": "0.5647284"
}
```
# How to run

To run the project you need pre-installed maven, kafka and JDK 1.8.
Provide correct bootstrap address in an application.properties file to connect with Kafka. 
```
kafka.bootstrapAddress = localhost:9092
```
Build an application with maven 
```
mvn clean install
```
To run an application call 
```sh
java -jar VehicleTracking-1.0-SNAPSHOT.jar
```