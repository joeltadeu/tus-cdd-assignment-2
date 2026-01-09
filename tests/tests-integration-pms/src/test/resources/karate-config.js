function fn() {
    let config = {};

    karate.configure('connectTimeout', 120000)
    karate.configure('readTimeout', 120000)

    let env = karate.env ? karate.env : 'local';
    config.env = env

    if (java.lang.System.getenv("URL") == null) {
        if (env == 'k8s') {
            config.patientHost = 'http://pms.local'
            config.doctorHost = 'http://pms.local'
            config.appointmentHost = 'http://pms.local'
        } else if (env == 'swarm') {
            config.patientHost = 'http://52.213.65.243:9081'
            config.doctorHost = 'http://63.33.204.251:9082'
            config.appointmentHost = 'http://34.244.77.146:9083'
        } else if (env == 'local') {
            config.patientHost = 'http://localhost:9081'
            config.doctorHost = 'http://localhost:9082'
            config.appointmentHost = 'http://localhost:9083'
        }
    }

    console.log('Patient URL: ', config.patientHost);
    console.log('Doctor URL: ', config.doctorHost);
    console.log('Appointment URL: ', config.appointmentHost);
    config.config = read('_config.json')
    return config;
}