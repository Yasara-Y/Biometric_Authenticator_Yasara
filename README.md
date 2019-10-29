# Biometric_Authenticator_Yasara
This is my repo with all the biometric files
Biometric authenticator and the new endpoint.

Import a CA signed certificate to create a secured http connection with the WSO2 IS.

Firebase server key of the android app : AAAAJp0GZ-I:APA91bHfqf4eWjGbQSIPE6o_gEduOTPhEeOEdoVu5sEN106kbI30-YY8J9ySsh0Vj3QDCd7ovqjVlhyMuEkuLapQNYe6Ta7of0aT4FFAOLqZ3E_FrbSZWhn5kyypaRV-bFHS34Falvnn

Enter this Firebase Server key in WSO2 management console->Identity Provider->BiometricAuthenticator Configuration.

Register a user and a Service Provider in the management console.

As of now, since the databases are hardcoded, obtain your device ID from the debug Logs of Android studio application, and include it and the registered username in the hashmap "tokenList" in biometricAuthenticator.java.
