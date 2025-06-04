pipeline {
    agent any

    environment {
        ANDROID_HOME = '/Users/admin/Library/Android/sdk'
        ANDROID_SDK_ROOT = '/Users/admin/Library/Android/sdk'
        JAVA_HOME = '/opt/homebrew/opt/openjdk@17'
        NPM_GLOBAL = '/Users/admin/.npm-global'
        PATH = "/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/emulator:${ANDROID_HOME}/cmdline-tools/latest/bin:${JAVA_HOME}/bin:${NPM_GLOBAL}/bin"
        AVD_NAME = 'ci_emulator'
        AVD_PACKAGE = 'system-images;android-30;google_apis;arm64-v8a'
        AVD_DEVICE = 'pixel_4'
    }

    stages {
        stage('Install Appium') {
            steps {
                sh '''
                    echo "Setting up npm global directory..."
                    mkdir -p $NPM_GLOBAL
                    npm config set prefix $NPM_GLOBAL

                    echo "Installing Appium globally..."
                    npm install -g appium

                    echo "Verifying Appium installation..."
                    export PATH=$NPM_GLOBAL/bin:$PATH
                    appium --version
                '''
            }
        }

        stage('Start Appium Server') {
            steps {
                sh '''
                    echo "Starting Appium server in the background..."
                    export PATH=$NPM_GLOBAL/bin:$PATH
                    pkill -f appium || true
                    nohup appium --base-path /wd/hub > appium.log 2>&1 &
                    for i in {1..15}; do
                        if nc -z localhost 4723; then
                            echo "Appium server started."
                            break
                        fi
                        echo "Waiting for Appium server to start..."
                        sleep 2
                    done
                '''
            }
        }

        stage('Verify Tools') {
            steps {
                sh '''
                    export PATH=$NPM_GLOBAL/bin:$PATH
                    echo "Checking for required tools..."
                    command -v brew   || { echo "Homebrew not found! Please install it on the Jenkins agent."; exit 1; }
                    command -v mvn    || { echo "Maven not found! Please install it on the Jenkins agent."; exit 1; }
                    command -v node   || { echo "Node.js not found! Please install it on the Jenkins agent."; exit 1; }
                    command -v appium || { echo "Appium not found! Please install it on the Jenkins agent with: npm install -g appium"; exit 1; }
                    command -v adb    || { echo "Android SDK platform-tools (adb) not found! Please install it on the Jenkins agent."; exit 1; }
                    command -v emulator || { echo "Android emulator not found! Please install it on the Jenkins agent."; exit 1; }
                    echo "All required tools are present."
                '''
            }
        }

        stage('Setup Android Emulator') {
            steps {
                sh '''
                    echo "Ensuring ARM64 system image is installed..."
                    $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "${AVD_PACKAGE}"

                    echo "Removing old AVD if it exists..."
                    if $ANDROID_HOME/emulator/emulator -list-avds | grep -q "^${AVD_NAME}$"; then
                        $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager delete avd -n "${AVD_NAME}"
                    fi

                    echo "Creating new ARM64 AVD..."
                    echo "no" | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd \
                        --name "${AVD_NAME}" \
                        --package "${AVD_PACKAGE}" \
                        --device "${AVD_DEVICE}" \
                        --force

                    echo "Available AVDs:"
                    $ANDROID_HOME/emulator/emulator -list-avds
                '''
            }
        }

        stage('Start Emulator') {
            steps {
                sh '''
                    echo "Starting emulator in headless mode..."
                    $ANDROID_HOME/emulator/emulator -avd "${AVD_NAME}" -no-window -no-audio -no-boot-anim -wipe-data > /dev/null 2>&1 &
                    EMULATOR_PID=$!

                    echo "Waiting for emulator to boot..."
                    adb wait-for-device
                    boot_completed=""
                    until [ "$boot_completed" = "1" ]; do
                        sleep 3
                        boot_completed=$(adb shell getprop sys.boot_completed | tr -d '\r')
                        echo "   Boot completed: $boot_completed"
                    done
                    echo "Emulator is ready."
                '''
            }
        }

        stage('Install Calculator App') {
            steps {
                sh '''
                    echo "Installing Calculator APK on emulator..."
                    adb install -r apps/Calculator.apk
                    echo "Calculator app installed."
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh '''
                    export PATH=$NPM_GLOBAL/bin:$PATH
                    echo "Running tests..."
                    mvn clean test
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
                echo "Stopping Appium server (if running)..."
                pkill -f appium || true
                echo "Stopping Android emulator (if running)..."
                $ANDROID_HOME/platform-tools/adb emu kill || true
            '''
        }
    }
}