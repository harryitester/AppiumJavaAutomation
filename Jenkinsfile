pipeline {
    agent any

    environment {
        ANDROID_HOME = '/Users/admin/Library/Android/sdk'
        ANDROID_SDK_ROOT = '/Users/admin/Library/Android/sdk'
        JAVA_HOME = '/opt/homebrew/opt/openjdk@17'
        PATH = "/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/emulator:${ANDROID_HOME}/cmdline-tools/latest/bin:${JAVA_HOME}/bin"
        EMULATOR_NAME = 'ci_emulator'
    }

    stages {
        stage('Verify Tools') {
            steps {
                sh '''
                    echo "Checking for required tools..."
                    command -v brew || { echo "Homebrew not found! Please install it on the Jenkins agent."; exit 1; }
                    command -v mvn || { echo "Maven not found! Please install it on the Jenkins agent."; exit 1; }
                    command -v node || { echo "Node.js not found! Please install it on the Jenkins agent."; exit 1; }
                    command -v appium || { echo "Appium not found! Please install it on the Jenkins agent."; exit 1; }
                    command -v adb || { echo "Android SDK platform-tools (adb) not found! Please install it on the Jenkins agent."; exit 1; }
                '''
            }
        }

        stage('Setup Android Emulator (ARM64)') {
            steps {
                sh '''
                    # Install ARM64 system image if not present
                    $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-30;google_apis;arm64-v8a"

                    # Delete old AVD if it exists
                    if $ANDROID_HOME/emulator/emulator -list-avds | grep -q "Pixel_4_API_30"; then
                        $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager delete avd -n Pixel_4_API_30
                    fi

                    # Create new ARM64 AVD
                    echo "no" | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd \
                        --name Pixel_4_API_30 \
                        --package "system-images;android-30;google_apis;arm64-v8a" \
                        --device "pixel_4" \
                        --force
                '''
            }
        }

        stage('Start Emulator') {
            steps {
                sh '''
                    echo "Creating AVD if it doesn't exist..."
                    echo "no" | avdmanager create avd -n $EMULATOR_NAME -k "system-images;android-30;google_apis;x86" --force || true

                    echo "Starting emulator in headless mode..."
                    $ANDROID_HOME/emulator/emulator -avd $EMULATOR_NAME -no-window -no-audio -no-boot-anim -wipe-data &
                    EMULATOR_PID=$!

                    echo "Waiting for emulator to boot..."
                    adb wait-for-device
                    boot_completed=""
                    until [ "$boot_completed" = "1" ]; do
                        sleep 5
                        boot_completed=$(adb shell getprop sys.boot_completed | tr -d '\r')
                        echo "Boot completed: $boot_completed"
                    done
                    echo "Emulator is ready."
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh '''
                    echo "Running tests..."
                    mvn clean test
                '''
            }
        }
    }

    post {
        always {
            sh '''
                echo "Stopping Appium server..."
                pkill -f appium || true
                echo "Stopping Android emulator..."
                $ANDROID_HOME/platform-tools/adb emu kill || true
            '''
        }
    }
}