Task Manager with UI - accessible under http://localhost:8080/tasks/view. 
To access the app:
1) ./gradlew clean build
2) docker compose up --build (use --build if building the images for the first time)
3) access http://localhost:8080/tasks/view
4) enjoy features
5) docker compose down (shut down the app and stop docker containers)
6) docker compose up( if you want to acess the app again - the database is persistent and last changes are saved)
