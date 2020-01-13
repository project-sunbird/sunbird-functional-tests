@Library('deploy-conf')_
pipeline {
    environment {
        sunbird_api_key = "${sunbird_api_keys}"
        content_store_api_base_url = "https://dev.sunbirded.org/api"
        content_store_api_key="${sunbird_api_keys}"
        sunbird_cassandra_host = "10.10.4.4"
        sunbird_cassandra_port = "9042"
        sunbird_cassandra_username = "cassandra"
        sunbird_cassandra_keyspace = "sunbird"
        sunbird_sso_username = "shailesh-admin"
        sunbird_sso_password = "${sunbird_sso_passwords}"
        sunbird_sso_realm = "sunbird"
        sunbird_sso_client_id = "admin-cli"
        sunbird_es_host = "10.10.3.7"
        sunbird_es_port = "9300"
        sunbird_es_index = "searchindex"
        sunbird_test_base_url = "https://dev.sunbirded.org"
        sunbird_sso_url="https://dev.sunbirded.org/auth"
        sunbird_username="ft_org_admin@org.com"
        sunbird_password="password"
        sunbird_default_channel="ft_channel_01"
        sunbird_test_email_address_1 = "sunbirdtest101@gmail.com"
        sunbird_test_email_address_2 = "sunbirdtest102@gmail.com"
        sunbird_content_id = "do_112728688963133440110506"
        sunbird_user_framework_board = "NCERT"
        file_upload_max_size=2
        sunbird_user_framework_grade_level = "Grade 1"
        sunbird_user_framework_medium = "English"
        sunbird_user_framework_subject = "Math"
        content_reviewer_user="ft_reviewer2@org.com"
        content_reviewer_password="password"
        sunbird_user_framework_id="NCFCOPY"
    }

    agent { label "build-slave" }

    stages {
        stage("build") {
            steps {
                script{
                try{
                sh '''
                cd sunbird_service_api_test
                ls
                mvn -X clean verify
                '''
            }
            finally {
                archiveArtifacts 'sunbird_service_api_test/target/target/citrus-reports/citrus-test-results.html'
		    email_notify("${learner_team_email_group}")    
	    }
        }
}
}
}
}
