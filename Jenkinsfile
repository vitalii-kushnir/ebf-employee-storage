pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('Generate Report') {
      steps {
        junit '**/target/*.xml'
      }
    }
    stage('Deploy') {
      steps {
        sh 'mvn package'
      }
    }
  }
}
