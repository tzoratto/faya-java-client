try {
    properties([[$class: 'BuildDiscarderProperty',
                 strategy: [$class: 'LogRotator',
                            artifactDaysToKeepStr: '',
                            artifactNumToKeepStr: '30',
                            daysToKeepStr: '',
                            numToKeepStr: '30']
                ]])
    currentBuild.result = "SUCCESS"
    node('linux') {

        stage('Checkout') {
            checkout scm
        }

        stage('Build') {
            def javaHome = tool 'jdk8'
            def mvnHome = tool 'Maven 3.3.9'
            withEnv(["JAVA_HOME=${javaHome}", "PATH+TOOLS=${javaHome}/bin:${mvnHome}/bin"]) {
                configFileProvider(
                        [configFile(fileId: 'tzoratto', variable: 'MAVEN_SETTINGS')]) {
                    sh "mvn --batch-mode -s $MAVEN_SETTINGS -V -U -e -Dmaven.repo.local=$WORKSPACE/.repository clean deploy"
                }
            }
        }

        if (env.BRANCH_NAME == 'master') {

            stage ('Quality analysis') {
                def javaHome = tool 'jdk8'
                def mvnHome = tool 'Maven 3.3.9'
                withEnv(["JAVA_HOME=${javaHome}", "PATH+TOOLS=${javaHome}/bin:${mvnHome}/bin"]) {
                    withSonarQubeEnv('sonar') {
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
                    }
                }
            }
        }
    }
}
catch (err) {

    currentBuild.result = "FAILURE"

    mail bcc: '',
            body: "Build number : ${env.BUILD_NUMBER}, error : ${err}. Go to ${env.BUILD_URL}",
            cc: '',
            from: '',
            replyTo: '',
            subject: "${env.JOB_NAME} : pipeline failed",
            to: 'thomas.zoratto@gmail.com'

    throw err
}