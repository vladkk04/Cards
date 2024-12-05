***Cards*** is a mobile application designed for learning Finnish using cards. In the app, users can see a word in Finnish as well as its translation into Russian. It is a great way to quickly memorise key words and phrases in Finnis

## Features:
- Display of words in Finnish with their Russian translations.
- Simple interface for easy navigation.
- Memorisation methodology through cards to improve learning.

# How to Launch App Using Android Studio

## Prerequisites
Before you begin, ensure you have the following:

- Android Studio installed on your machine.
- A Java Development Kit (JDK) installed (typically included with Android Studio).
- Android SDK and required tools installed via Android Studio.
- A real Android device (optional for testing on a physical device).

## Step 1: Open Your Project in Android Studio
1. Launch Android Studio.
2. If you already have an Android project, click **Open** and navigate to your project directory.
3. If you don’t have a project, click **Start a New Android Studio Project** and follow the setup wizard to create your app.

## Step 2: Configure the Android Emulator (Optional, for testing on a virtual device)

### Create an Emulator:
1. In Android Studio, go to the **AVD Manager** (Android Virtual Device Manager) by clicking the **Tools** menu and selecting **AVD Manager**.
2. Click **Create Virtual Device**.
3. Choose the device model (e.g., Pixel 4) and click **Next**.
4. Select a system image (e.g., Android 12) and click **Next**.
5. Verify your configuration settings and click **Finish** to create the virtual device.

### Launch the Emulator:
1. In the **AVD Manager**, click the **Play** button next to the device you just created. This will start the emulator.
2. Wait for the emulator to boot up fully.

## Step 3: Set Up Your Physical Device (Optional, for testing on a real device)

### Enable Developer Options on Your Android Device:
1. Open **Settings** on your Android device.
2. Scroll to **About phone** and tap it.
3. Find and tap **Build number** seven times to enable Developer Options.
4. Go back to the Settings menu and open **Developer options**.
5. Enable **USB debugging**.

### Connect Your Device via USB:
1. Use a USB cable to connect your Android device to your computer.
2. You may be prompted on your device to allow USB debugging. Tap **Allow**.
3. Ensure your device is detected by Android Studio by going to **Tools > Device Manager**.

### Install Device Drivers (if necessary):
- On Windows, you might need to install the device drivers for your specific Android device. These can often be found on the manufacturer's website.

## Step 4: Select the Deployment Target
1. In Android Studio, locate the **Device Selector** dropdown in the top-right corner.
2. You will see a list of available devices (both emulators and connected physical devices).
3. Select the device or emulator you want to run your app on.

## Step 5: Build and Run the App

### Run the App:
1. Click the green **Run** button (the play icon) in the top toolbar of Android Studio.
2. Android Studio will first compile the app and install it on the selected device or emulator.

### Wait for the App to Launch:
- The app will be installed and opened automatically on the emulator or connected device once the build is complete.

## Troubleshooting Tips

### If the app doesn’t launch or the device isn't detected:
- Check if **USB debugging** is enabled on your physical device.
- Restart both Android Studio and your device/emulator.
- Ensure your device is properly connected and recognized in **Device Manager**.

### If the app crashes or throws errors:
- Use **Logcat** in Android Studio to review the stack trace and debug the issue.
- Set breakpoints in your code to inspect the flow of execution.
