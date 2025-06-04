pipeline {
    agent any
    
    environment {
        ANDROID_HOME = '/Users/admin/Library/Android/sdk'
        ANDROID_SDK_ROOT = '/Users/admin/Library/Android/sdk'
        JAVA_HOME = '/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Setup Environment') {
            steps {
                sh '''
                    echo "Setting up environment variables..."
                    export ANDROID_HOME=$ANDROID_HOME
                    export ANDROID_SDK_ROOT=$ANDROID_SDK_ROOT
                    export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
                '''
            }
        }
        
        stage('Start Appium Server') {
            steps {
                sh '''
                    echo "Starting Appium server..."
                    appium &
                    sleep 10
                '''
            }
        }
        
        stage('Start Android Emulator') {
            steps {
                sh '''
                    echo "Starting Android emulator..."
                    emulator -avd Pixel_4_API_30 -no-boot-anim &
                    sleep 30
                '''
            }
        }
        
        stage('Build and Test') {
            steps {
                sh '''
                    echo "Building and running tests..."
                    mvn clean test
                '''
            }
        }
        
        stage('Generate Allure Report') {
            steps {
                sh '''
                    echo "Generating Allure report..."
                    mvn allure:report
                '''
            }
        }
    }
    
    post {
        always {
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
            
            sh '''
                echo "Stopping Appium server..."
                pkill -f appium
                
                echo "Stopping Android emulator..."
                adb emu kill
            '''
            
            cleanWs()
        }
        
        success {
            echo 'Pipeline completed successfully!'
        }
        
        failure {
            echo 'Pipeline failed!'
        }
    }
} 