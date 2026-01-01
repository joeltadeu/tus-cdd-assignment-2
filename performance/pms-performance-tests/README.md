
mvn gatling:test -Dgatling.simulationClass=com.pms.performance.simulation.PmsLoadSimulation



mvn gatling:test -Dgatling.simulationClass=com.pms.performance.simulation.PmsLoadSimulation \
-DpatientUrl=http://52.213.65.243:9081 \
-DdoctorUrl=http://63.33.204.251:9082 \
-DappointmentUrl=http://34.244.77.146:9083 \
-Dusers=10 \
-DdurationMinutes=5