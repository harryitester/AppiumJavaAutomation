pipeline {
    agent any
    
    environment {
        ANDROID_HOME = '/Users/admin/Library/Android/sdk'
        ANDROID_SDK_ROOT = '/Users/admin/Library/Android/sdk'
        JAVA_HOME = '/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home'
        PATH = "${env.PATH}:${env.ANDROID_HOME}/tools:${env.ANDROID_HOME}/platform-tools:${env.ANDROID_HOME}/emulator:$NPM_HOME/.bin"
        NODE_HOME = '/usr/local/bin/node'
        NPM_HOME = '/usr/local/lib/node_modules'
        APPIUM_HOME = '/usr/local/lib/node_modules/appium'
        M2_HOME = '/usr/local/Cellar/maven/3.9.6/libexec'
        HOMEBREW_PREFIX = '/opt/homebrew'
    }
    
    stages {
        stage('Checkout latested code') {
            steps {
                checkout scm
            }
        }
        
        stage('Setup Test Environment') {
            steps {
                sh '''
                    echo "Setting up environment variables..."
                    export ANDROID_HOME=$ANDROID_HOME
                    export ANDROID_SDK_ROOT=$ANDROID_SDK_ROOT
                    export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$NPM_HOME/.bin
                    
                    echo "Verifying Homebrew installation..."
                    if [ ! -f "/opt/homebrew/bin/brew" ]; then
                        echo "Homebrew not found. Installing..."
                        # Create Homebrew directory with proper permissions
                        sudo mkdir -p /opt/homebrew
                        sudo chown -R $(whoami):admin /opt/homebrew
                        
                        # Download and install Homebrew
                        curl -L https://github.com/Homebrew/brew/tarball/master | tar xz --strip 1 -C /opt/homebrew
                        
                        # Set up Homebrew environment
                        echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.bash_profile
                        echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zshrc
                        eval "$(/opt/homebrew/bin/brew shellenv)"
                        
                        # Initialize Homebrew
                        /opt/homebrew/bin/brew update
                    fi
                    
                    # Ensure Homebrew is in PATH
                    eval "$(/opt/homebrew/bin/brew shellenv)"
                    
                    echo "Verifying Maven installation..."
                    if ! command -v mvn &> /dev/null; then
                        echo "Maven not found. Installing..."
                        /opt/homebrew/bin/brew install maven
                    fi
                    
                    echo "Setting up Maven environment..."
                    export M2_HOME=$M2_HOME
                    export PATH=$PATH:$M2_HOME/bin
                    
                    echo "Verifying Maven setup..."
                    mvn --version
                    
                    echo "Verifying Android SDK setup..."
                    if [ ! -d "$ANDROID_HOME/platform-tools" ]; then
                        echo "Android platform-tools not found. Installing..."
                        mkdir -p $ANDROID_HOME/platform-tools
                        curl -o platform-tools.zip https://dl.google.com/android/repository/platform-tools-latest-darwin.zip
                        unzip -q platform-tools.zip -d $ANDROID_HOME
                        rm platform-tools.zip
                    fi
                    
                    echo "Verifying platform-tools installation..."
                    ls -la $ANDROID_HOME/platform-tools
                    $ANDROID_HOME/platform-tools/adb version
                    
                    echo "Verifying Node.js installation..."
                    if ! command -v node &> /dev/null; then
                        echo "Node.js not found. Installing..."
                        /opt/homebrew/bin/brew install node
                    fi
                    
                    echo "Verifying npm installation..."
                    if ! command -v npm &> /dev/null; then
                        echo "npm not found. Installing..."
                        curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
                        export NVM_DIR="$HOME/.nvm"
                        [ -s "$NVM_DIR/nvm.sh" ] && source "$NVM_DIR/nvm.sh"
                        nvm install node
                    fi
                '''
            }
        }
        
        stage('Install Appium') {
            steps {
                sh '''
                    echo "Installing Appium..."
                    sudo npm install -g appium
                    sudo npm install -g appium-doctor
                    
                    echo "Verifying Appium installation..."
                    which appium
                    appium --version
                    appium-doctor --android
                    
                    echo "Setting up Appium environment..."
                    export PATH=$PATH:$NPM_HOME/.bin
                    echo $PATH
                '''
            }
        }
        
        stage('Start Appium Server') {
            steps {
                sh '''
                    echo "Starting Appium server..."
                    export PATH=$PATH:$NPM_HOME/.bin
                    which appium
                    
                    # Start Appium server with full path
                    $NPM_HOME/.bin/appium &
                    APPIUM_PID=$!
                    sleep 10
                    
                    echo "Verifying Appium server is running..."
                    if ! ps -p $APPIUM_PID > /dev/null; then
                        echo "Appium server failed to start"
                        exit 1
                    fi
                    
                    echo "Appium server started successfully with PID: $APPIUM_PID"
                '''
            }
        }
        
        stage('Start Android Emulator') {
            steps {
                sh '''
                    echo "Starting Android emulator..."
                    if [ ! -d "$ANDROID_HOME/emulator" ]; then
                        echo "Android emulator not found. Installing..."
                        sdkmanager "emulator"
                    fi
                    
                    $ANDROID_HOME/emulator/emulator -avd Pixel_4_API_30 -no-boot-anim &
                    sleep 30
                    
                    echo "Waiting for emulator to be ready..."
                    $ANDROID_HOME/platform-tools/adb wait-for-device
                    $ANDROID_HOME/platform-tools/adb shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done;'
                '''
            }
        }
        
        stage('Build and Test') {
            steps {
                sh '''
                    echo "Building and running tests..."
                    export PATH=$PATH:$M2_HOME/bin
                    which mvn
                    mvn clean test
                '''
            }
        }
        
        stage('Generate Allure Report') {
            steps {
                sh '''
                    echo "Generating Allure report..."
                    export PATH=$PATH:$M2_HOME/bin
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
                $ANDROID_HOME/platform-tools/adb emu kill
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